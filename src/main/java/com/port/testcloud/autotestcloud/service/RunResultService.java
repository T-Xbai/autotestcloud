package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.domain.RunResult;

import java.util.List;

public interface RunResultService {

    RunResult findOne(String id);

    List<RunResult> findByCaseId(String caseId);

    List<RunResult> findByRunId(String runId);

    RunResult save(RunResult runResult);

    void isExist(String id);


}
