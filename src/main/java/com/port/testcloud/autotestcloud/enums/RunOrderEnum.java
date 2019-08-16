package com.port.testcloud.autotestcloud.enums;

import lombok.Getter;

/**
 * @ClassName: RunOrderEnum
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-13 15:35
 * @Description:
 */
@Getter
public enum  RunOrderEnum {

    BEFORE(0,"运行前执行"),
    AFTER(-1,"运行结束执行")


    ;

    private Integer code;

    private String mes;

    RunOrderEnum(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }
}
