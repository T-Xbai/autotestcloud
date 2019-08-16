package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.RunResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunResultRepository extends JpaRepository<RunResult,String> {

    List<RunResult> findByCaseId(String caseId);

    List<RunResult> findByRunId(String runId);

    RunResult findByCaseIdAndRunId(String caseId,String runId);

}
