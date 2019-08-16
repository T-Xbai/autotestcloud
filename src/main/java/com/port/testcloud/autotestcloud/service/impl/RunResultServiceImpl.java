package com.port.testcloud.autotestcloud.service.impl;

import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.repository.RunResultRepository;
import com.port.testcloud.autotestcloud.service.RunResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName: RunResultServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-05 16:48
 * @Description: 运行结果
 */

@Service
@Slf4j
public class RunResultServiceImpl implements RunResultService {

    @Autowired
    private RunResultRepository resultRepository;

    @Override
    public RunResult findOne(String id) {
        return resultRepository.findById(id).orElse(new RunResult());
    }

    @Override
    public List<RunResult> findByCaseId(String caseId) {
        return resultRepository.findByCaseId(caseId);
    }

    @Override
    public List<RunResult> findByRunId(String runId) {
        return resultRepository.findByRunId(runId);
    }

    @Override
    public RunResult findByCaseIdAndRunId(String caseId, String runId) {
        return resultRepository.findByCaseIdAndRunId(caseId, runId);
    }

    @Override
    public void jointExceptionMsg(RunResult runResult, String prefixMsg ,Exception e) {
        String msg = "--------------------------------------------------------------------------> \n"
                .concat(prefixMsg)
                .concat(" \n")
                .concat(e.toString())
                .concat(" \n");
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            msg = msg.concat(stackTraceElement.toString());
        }

        String exceptionMessage = runResult.getExceptionMessage().concat(msg);
        runResult.setExceptionMessage(exceptionMessage);
    }

    @Override
    public RunResult save(RunResult runResult) {
        return resultRepository.save(runResult);
    }

    @Override
    public void isExist(String id) {
        try {
            resultRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            log.error("【运行详情结果】查询Id不存在：{}", id);
            throw new AutoTestException(ResultEnums.RUN_RESULT_ID_NOT_EXIST);
        }
    }
}
