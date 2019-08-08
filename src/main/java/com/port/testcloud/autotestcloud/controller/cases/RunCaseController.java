package com.port.testcloud.autotestcloud.controller.cases;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.domain.RunResult;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.service.cases.RunCaseService;
import com.port.testcloud.autotestcloud.service.cases.TestCaseService;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TestCaseService testCaseService;


    @GetMapping("/{caseId}")
    public ResultVO runCase(@PathVariable("caseId") String caseId) {
        TestCaseDto testCaseDto = testCaseService.findOne(caseId);
        if (testCaseDto == null || testCaseDto.getId() == null){
            log.error("【用例执行】caseId 不存在：{}",caseId);
            throw new AutoTestException(ResultEnums.TEST_CASE_NOT_EXIST);
        }


        RunResult response = runCaseService.runCase(caseId);

        return ResultVOUtil.success(response);
    }


}
