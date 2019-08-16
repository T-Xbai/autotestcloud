package com.port.testcloud.autotestcloud.convert.testcase;

import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: ToRunResult
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-12 22:56
 * @Description: 转换为 RunResultt
 */
public class ToRunResult {

    /**
     * 基础参数的转换
     */
    public static RunResult testCaseDtoTo( RunResult runResult ,TestCaseDto testCaseDto){
        // 写入执行结果
        InfoDto info = testCaseDto.getInfo();
        runResult.setId(KeyUtil.unique());
        runResult.setCaseId(testCaseDto.getId());
        runResult.setRunId(KeyUtil.unique());
        runResult.setRequestUrl(info.getRequestUrl());
        runResult.setRequestMethod(info.getRequestMethod());
        runResult.setRequestHeaders(info.getRequestHeaders());
        runResult.setRequestBody(info.getRequestBody());
        return runResult;
    }


    public static RunResult testCaseDtoTo(TestCaseDto testCaseDto){
        return ToRunResult.testCaseDtoTo(new RunResult(),testCaseDto);
    }


}
