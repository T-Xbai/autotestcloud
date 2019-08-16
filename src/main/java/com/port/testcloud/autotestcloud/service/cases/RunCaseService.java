package com.port.testcloud.autotestcloud.service.cases;

import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.domain.TestCase;
import com.port.testcloud.autotestcloud.dto.DataVariableDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RunCaseService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 22:49
 * @Description:
 */
public interface RunCaseService {


    /**
     * 根据 caseId 执行
     */
    Response runCase(TestCaseDto testCaseDto, RunResult runResult);

    RunResult runCase(TestCaseDto testCaseDto, String runId);

    String checkResult(Response response, String checkResult);

    TestCaseDto replaceData(TestCaseDto testCaseDto);

    List<DataVariableDto> runDependCase(String caseId, RunResult runResult);

    void projectByRunCase(String projectId, String runId);


}
