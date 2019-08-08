package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: RunCaseService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 22:49
 * @Description:
 */
public interface RunCaseService {


    /**
     * 根据 caseId 执行
     * @param caseId
     */
    RunResult runCase(String caseId);

    RunResult runCase(String caseId,String runId);

    void projectByRunCase(String projectId);


}
