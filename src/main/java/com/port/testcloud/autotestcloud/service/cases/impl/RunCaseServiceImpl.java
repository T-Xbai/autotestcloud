package com.port.testcloud.autotestcloud.service.cases.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.AssertResultDto;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.RequestService;
import com.port.testcloud.autotestcloud.service.RunResultService;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RunCaseServiceImpl implements RunCaseService {


    @Autowired
    private RequestService requestService;

    @Autowired
    private TestCaseService caseService;

    @Autowired
    private RunResultService runResultService;

    private String responseBody = "";

    @Override
    public RunResult runCase(String caseId) {
        return this.runCase(caseId, KeyUtil.unique());
    }

    @Override
    @Transactional
    public RunResult runCase(String caseId, String runId) {

        TestCaseDto testCaseDto = caseService.findOne(caseId);

        Response response = null;
        try {
            Class<? extends RequestService> aClass = requestService.getClass();
            Method method = aClass.getMethod(testCaseDto.getInfo().getRequestMethod(), TestCaseDto.class);
            response = (Response) method.invoke(requestService, testCaseDto);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 运行结果写入，并保存
        RunResult runResult = new RunResult();
        Request request = response.request();
        this.responseBody = this.getBody(response);

        runResult.setId(KeyUtil.unique());
        runResult.setCaseId(caseId);
        runResult.setRunId(runId);
        if (request != null) {
            runResult.setRequestUrl(request.url().toString());
            runResult.setRequestMethod(request.method());
            runResult.setRequestHeaders(request.headers().toString());
            runResult.setRequestBody(testCaseDto.getInfo().getRequestBody());
        }

        runResult.setStatusCode(response.code());
        runResult.setResponseBody(this.responseBody);
        String assertResult = this.checkResult(response, testCaseDto.getInfo().getCheckResult());
        runResult.setCheckResult(assertResult);
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

    @Override
    public void projectByRunCase(String projectId) {

    }

    public static void main(String[] args) {
       Object value = "403";

        int i = Integer.parseInt(value.toString());
        System.out.println(i);
    }
}
