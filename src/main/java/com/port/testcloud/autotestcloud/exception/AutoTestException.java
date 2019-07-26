package com.port.testcloud.autotestcloud.exception;

import com.port.testcloud.autotestcloud.enums.ResultEnums;

/**
 * @ClassName: AutoTestException
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 10:22
 * @Description:
 */
public class AutoTestException extends RuntimeException {


    private Integer code;


    public AutoTestException(ResultEnums resultEnums) {
        super(resultEnums.getMes());
        this.code = resultEnums.getCode();
    }



}
