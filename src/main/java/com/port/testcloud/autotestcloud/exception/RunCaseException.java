package com.port.testcloud.autotestcloud.exception;

import com.port.testcloud.autotestcloud.enums.ResultEnums;
import lombok.Getter;

/**
 * @ClassName: RunCaseException
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-15 00:13
 * @Description: 运行case 统一异常处理
 */
@Getter
public class RunCaseException extends RuntimeException {

    private String caseId;

    private String runId;


    // TODO 用例执行，统一异常处理，记录运行异常结果
    public RunCaseException(String caseId,String runId,String message) {
        super(message);
        this.caseId = caseId;
        this.runId = runId;
    }

}
