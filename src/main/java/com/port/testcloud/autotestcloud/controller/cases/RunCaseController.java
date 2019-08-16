package com.port.testcloud.autotestcloud.controller.cases;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.convert.testcase.ToRunResult;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.DataVariableDto;
import com.port.testcloud.autotestcloud.dto.InfoDto;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.service.RunResultService;
import com.port.testcloud.autotestcloud.service.cases.DbOperationService;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.service.projects.ProjectsService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ReplaceUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: RunCaseController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 11:26
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/run/case")
public class RunCaseController {


    @Autowired
    private RunCaseService runCaseService;

    @Autowired
    private TestCaseService caseService;

    @Autowired
    private RunResultService runResultService;

    @Autowired
    private DbOperationService dbOperationService;

    @Autowired
    private ProjectsService projectsService;


    @GetMapping("/{caseId}")
    public ResultVO runCase(@PathVariable("caseId") String caseId) {

        TestCaseDto testCaseDto = caseService.findOne(caseId);
        if (testCaseDto == null || testCaseDto.getId() == null) {
            log.error("【用例执行】caseId 不存在：{}", caseId);
            throw new AutoTestException(ResultEnums.TEST_CASE_NOT_EXIST);
        }


        RunResult runResult = runCaseService.runCase(testCaseDto, KeyUtil.unique());

        dbOperationService.afterExecute(runResult, runResult.getResponseBody(), caseId);

        runResultService.save(runResult);

//        String runId = KeyUtil.unique();
//        RunResult runResult = runResultService.findByCaseIdAndRunId(caseId, runId);
//        if (runResult == null){
//            runResult = new RunResult();
//            runResult.setId(KeyUtil.unique());
//            runResult.setCaseId(caseId);
//            runResult.setRunId(runId);
//        }
//
//        //数据库依赖执行
//        dbOperationService.beforeExecute(caseId);
//        Response response = runCaseService.runCase(testCaseDto, runResult);
//
//
//        // 校验预期结果
//        String checkResultMsg = runCaseService.checkResult(response,testCaseDto.getInfo().getCheckResult());
//        runResult.setCheckResult(checkResultMsg);
//
//        dbOperationService.afterExecute(runResult,runResult.getResponseBody(),caseId);
//        runResultService.save(runResult);
        return ResultVOUtil.success(runResult);
    }


    @GetMapping("/project/{projectId}")
    public ResultVO runProjectAllCase(@PathVariable("projectId") String projectId) {
        projectsService.isExist(projectId);
        runCaseService.projectByRunCase(projectId,KeyUtil.unique());
        return ResultVOUtil.success();
    }

}
