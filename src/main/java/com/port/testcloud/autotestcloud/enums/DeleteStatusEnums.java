package com.port.testcloud.autotestcloud.enums;

import lombok.Getter;

/**
 * @ClassName: DeleteStatusEnums
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 18:41
 * @Description: 删除状态
 */
@Getter
public enum DeleteStatusEnums {

    NORMAL(0,"正常"),
    DEL(-1,"已删除")


    ;

    private Integer code;

    private String mes;

    DeleteStatusEnums(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }
}
