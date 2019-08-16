package com.port.testcloud.autotestcloud.service.cases.impl;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.port.testcloud.autotestcloud.domain.DependCase;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.*;
import com.port.testcloud.autotestcloud.enums.DeleteStatusEnums;
import com.port.testcloud.autotestcloud.service.DataVariableService;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.service.RunResultService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.DependCaseService;
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

    @Autowired
    private DependCaseService dependCaseService;

    @Autowired
    private DbOperationService dbOperationService;

    private String responseBody = "";


    @Override
    @Transactional
    public Response runCase(TestCaseDto testCaseDto, RunResult runResult) {

        // 获取用例信息,并替换项目变量
        testCaseDto = this.replaceData(testCaseDto);
        final String caseId = testCaseDto.getId();


        // 判断是否有依赖case，并执行，并替换依赖变量值
        List<DataVariableDto> dataVariableDtoList = this.runDependCase(caseId, runResult);
        testCaseDto = ReplaceUtil.replaceTestCaseDto(dataVariableDtoList, testCaseDto);

        // 执行用例
        Response response = null;
        try {
            Class<? extends RequestService> aClass = requestService.getClass();
            Method method = aClass.getMethod(testCaseDto.getInfo().getRequestMethod(), TestCaseDto.class);
            response = (Response) method.invoke(requestService, testCaseDto);

            Request request = response.request();
            runResult.setStatusCode(response.code());
            runResult.setRequestHeaders(request.headers().toString());
            this.responseBody = this.getBody(response);
            runResult.setResponseBody(this.responseBody);
        } catch (Exception e) {
            // 写入异常信息
            runResultService.jointExceptionMsg(runResult, "【用例执行】用例数据错误：", e);
            log.error("【用例执行】用例数据错误：{}", e.toString());
        }

        InfoDto info = testCaseDto.getInfo();
        runResult.setRequestUrl(info.getRequestUrl());
        runResult.setRequestMethod(info.getRequestMethod());
        runResult.setRequestBody(info.getRequestBody());

        return response;
    }

    @Override
    public RunResult runCase(TestCaseDto testCaseDto, String runId) {
        final String caseId = testCaseDto.getId();
        RunResult runResult = runResultService.findByCaseIdAndRunId(caseId, runId);
        if (runResult == null) {
            runResult = new RunResult();
            runResult.setId(KeyUtil.unique());
            runResult.setCaseId(caseId);
            runResult.setRunId(runId);
        }

        //数据库依赖执行
        dbOperationService.beforeExecute(caseId);
        Response response = this.runCase(testCaseDto, runResult);


        // 校验预期结果
        String checkResultMsg = this.checkResult(response, testCaseDto.getInfo().getCheckResult());
        runResult.setCheckResult(checkResultMsg);
        return runResult;
    }


    /**
     * 验证预期结果
     */
    @Override
    public String checkResult(Response response, String checkResult) {
        Map<String, Object> map = JsonUtil.toMap(checkResult);
        List<AssertResultDto> assertResultDtos = new ArrayList<>();

        map.keySet().forEach(key -> {

            AssertResultDto assertResultDto = new AssertResultDto();
            Object value = map.get(key);
            assertResultDto.setExpect(key.concat(":").concat(String.valueOf(value)));

            if (response == null) {
                assertResultDto.setStatus(false);
                assertResultDto.setPractical("请检查请求数据是否正确");
            } else {
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
     *
     * @param testCaseDto
     * @return
     */
    public TestCaseDto replaceData(TestCaseDto testCaseDto) {
        List<DataVariableDto> dataVariableDtos = this.getDataVariable(testCaseDto);
        String testCaseDtoStr = ReplaceUtil.all(dataVariableDtos, JsonUtil.toJson(testCaseDto));
        return (TestCaseDto) JsonUtil.fromJson(testCaseDtoStr, TestCaseDto.class);
    }

    /**
     * 根据caseId 获取项目变量
     *
     * @param testCaseDto
     * @return
     */
    private List<DataVariableDto> getDataVariable(TestCaseDto testCaseDto) {
        String moduleId = testCaseDto.getModuleId();
        ModuleDto moduleDto = projectModuleService.findOne(moduleId);
        if (moduleDto == null || StringUtils.isEmpty(moduleDto.getId())) {
            log.error("【替换用例变量】数据格式错误，moduleId 不存在：{}", moduleId);
            return new ArrayList<>();
        }
        String projectId = moduleDto.getProjectId();
        Projects projects = projectsService.findOne(projectId);
        if (projects == null || StringUtils.isEmpty(projects.getId())) {
            log.error("【替换用例变量】数据格式错误，projectsId 不存在：{}", projectId);
            return new ArrayList<>();
        }

        return dataVariableService.get(projects.getProjectVariable());
    }


    /**
     * 用例执行前运行，依赖case
     *
     * @param caseId 用例Id
     * @return 依赖Case的变量
     */
    @Transactional
    @Override
    public List<DataVariableDto> runDependCase(String caseId, RunResult runResult) {
        List<DataVariableDto> dataVariableList = new ArrayList<>();

        List<DependCase> dependCaseList = dependCaseService.findByBeforeCase(caseId);
        dependCaseList.forEach(dependCase -> {
            this.runCase(caseService.findOne(dependCase.getDependCase()), runResult);
            String dependParams = dependCase.getDependParams();
            Map<String, Object> dependParamMap = JsonUtil.toMap(dependParams);
            dependParamMap.keySet().forEach(key -> {
                        DataVariableDto dataVariableDto = new DataVariableDto();
                        String expression = String.valueOf(dependParamMap.get(key));
                        dataVariableDto.setKey(key);

                        if (expression.substring(0, 2).equals("$.")) {
                            Object variableValue = "{{ " + key + " }}";
                            try {
                                variableValue = JsonPath.parse(runResult.getResponseBody()).read(expression);
                            } catch (Exception e) {
                                final String errorMes = "【Json path 匹配】匹配格式错误:";
                                runResultService.jointExceptionMsg(runResult, errorMes, e);
                                log.error("【Json path 匹配】匹配格式错误:{}", e.toString());
                            }
                            dataVariableDto.setValue(String.valueOf(variableValue));

                        } else {
                            Pattern pattern = Pattern.compile(expression);
                            Matcher matcher = pattern.matcher(runResult.getResponseBody());
                            String patternValue = "";
                            if (matcher.find()) {
                                patternValue = matcher.group(1);
                            }
                            dataVariableDto.setValue(patternValue);
                        }
                        dataVariableList.add(dataVariableDto);
                    }
            );
        });
        return dataVariableList;
    }


    @Override
    public void projectByRunCase(String projectId, String runId) {
        log.warn("RunId:{}",runId);
        List<ModuleDto> firstModuleList = projectModuleService.findAllByProjectId(projectId);
        firstModuleList.forEach(firstModule -> this.runModuleByCase(firstModule.getId(), runId));
    }


    private void runModuleByCase(String moduleId, String runId) {

        List<TestCaseDto> testCaseDtoList = caseService.findAll(moduleId);
        testCaseDtoList.forEach(testCaseDto -> {
            RunResult runResult = this.runCase(testCaseDto, runId);
            dbOperationService.afterExecute(runResult, runResult.getResponseBody(), testCaseDto.getId());
            log.info("用例 -> {} 执行结果: {}", testCaseDto.getCaseName(), runResult);
            runResultService.save(runResult);
        });

        List<ModuleDto> moduleDtoList = projectModuleService.findByParentIdAndIsDelete(moduleId, DeleteStatusEnums.NORMAL.getCode());
        moduleDtoList.forEach(moduleDto -> this.runModuleByCase(moduleDto.getId(), runId));

    }

}
