package com.port.testcloud.autotestcloud.service.cases.impl;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.*;
import com.port.testcloud.autotestcloud.service.DataVariableService;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.service.RunResultService;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.service.projects.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.projects.ProjectsService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ReplaceUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RunCaseServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 22:55
 * @Description:
 */
@Service
@Slf4j
public class RunCaseServiceImpl implements RunCaseService {


    @Autowired
    private RequestService requestService;

    @Autowired
    private TestCaseService caseService;

    @Autowired
    private RunResultService runResultService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private ProjectModuleService projectModuleService;

    @Autowired
    private DataVariableService dataVariableService;

    private String responseBody = "";

    @Override
    public RunResult runCase(String caseId) {
        return this.runCase(caseId, KeyUtil.unique());
    }

    @Override
    @Transactional
    public RunResult runCase(String caseId, String runId) {

        TestCaseDto testCaseDto = this.replaceData(caseService.findOne(caseId));


        InfoDto info = testCaseDto.getInfo();
        RunResult runResult = new RunResult();
        runResult.setId(KeyUtil.unique());
        runResult.setCaseId(caseId);
        runResult.setRunId(runId);
        runResult.setRequestUrl(info.getRequestUrl());
        runResult.setRequestMethod(info.getRequestMethod());
        runResult.setRequestHeaders(info.getRequestHeaders());
        runResult.setRequestBody(info.getRequestBody());

        try {
            Class<? extends RequestService> aClass = requestService.getClass();
            Method method = aClass.getMethod(testCaseDto.getInfo().getRequestMethod(), TestCaseDto.class);
            Response response = (Response) method.invoke(requestService, testCaseDto);

            // 运行结果写入，并保存
            this.responseBody = this.getBody(response);
            runResult.setStatusCode(response.code());
            runResult.setResponseBody(this.responseBody);
            String assertResult = this.checkResult(response, testCaseDto.getInfo().getCheckResult());
            runResult.setCheckResult(assertResult);

        } catch (NoSuchMethodException | IllegalAccessException  | InvocationTargetException e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            String errorMes = "请检查请求数据：\n ";
            for (int i = 0; i < stackTrace.length; i++) {
                errorMes = errorMes.concat( stackTrace[i].toString()).concat("\n ");
            }
            log.error("【用例执行】用例数据错误：{}",errorMes);
            runResult.setExceptionMessage(errorMes);
        }


        runResultService.save(runResult);


        return runResult;
    }


    private String checkResult(Response response, String checkResult) {
        Map<String, Object> map = JsonUtil.toMap(checkResult);
        List<AssertResultDto> assertResultDtos = new ArrayList<>();

        map.keySet().forEach(key -> {

            AssertResultDto assertResultDto = new AssertResultDto();
            Object value = map.get(key);
            assertResultDto.setExpect(key.concat(":").concat(String.valueOf(value)));


            if (key.equals("code")) {
                // 校验响应状态
                if (Integer.parseInt(value.toString()) == response.code()) {
                    assertResultDto.setStatus(true);
                } else {
                    assertResultDto.setStatus(false);
                }
                assertResultDto.setPractical(response.code());

            } else if (key.substring(0, 2).equals("$.")) {
                // jsonpath 表达式提取值，校验
                try {
                    Object readValue = JsonPath.parse(this.responseBody).read(key);
                    if (readValue.toString().equals(value)) {
                        assertResultDto.setStatus(true);
                    } else {
                        assertResultDto.setStatus(false);
                    }
                    assertResultDto.setPractical(readValue);
                } catch (PathNotFoundException e) {
                    assertResultDto.setPractical(e.getMessage());
                    assertResultDto.setStatus(false);
                }
            } else {
                // 其它默认正则表达式提取
                Pattern pattern = Pattern.compile(key);
                Matcher matcher = pattern.matcher(this.responseBody);
                String patternValue = "";
                if (matcher.find()) {
                    patternValue = matcher.group(1);
                }

                if (patternValue.equals(value)) {
                    assertResultDto.setStatus(true);
                } else {
                    assertResultDto.setStatus(false);
                }
                assertResultDto.setPractical(patternValue);
            }

            assertResultDtos.add(assertResultDto);
        });
        return JsonUtil.toJson(assertResultDtos);
    }

    /**
     * 获取响应body
     *
     * @param response
     * @return
     */
    private String getBody(Response response) {
        String body = null;
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;

    }


    /**
     * 将变量赋值给请求数据
     * @param testCaseDto
     * @return
     */
    private TestCaseDto replaceData(TestCaseDto testCaseDto) {
        String moduleId = testCaseDto.getModuleId();
        ModuleDto moduleDto = projectModuleService.findOne(moduleId);
        if (moduleDto == null || StringUtils.isEmpty(moduleDto.getId())) {
            log.error("【替换用例变量】数据格式错误，moduleId 不存在：{}", moduleId);
            return testCaseDto;
        }
        String projectId = moduleDto.getProjectId();
        Projects projects = projectsService.findOne(projectId);
        if (projects == null || StringUtils.isEmpty(projects.getId())) {
            log.error("【替换用例变量】数据格式错误，projectsId 不存在：{}", projectId);
            return testCaseDto;
        }

        List<DataVariableDto> dataVariableDtos = dataVariableService.get(projects.getProjectVariable());
        String testCaseDtoStr = ReplaceUtil.all(dataVariableDtos, JsonUtil.toJson(testCaseDto));
        return (TestCaseDto) JsonUtil.fromJson(testCaseDtoStr, TestCaseDto.class);

    }


    @Override
    public void projectByRunCase(String projectId) {

    }


}
