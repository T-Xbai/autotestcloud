package com.port.testcloud.autotestcloud.enums;

import lombok.Getter;

/**
 * @ClassName: DbTypeEnums
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-14 22:46
 * @Description:
 */
@Getter
public enum DbTypeEnums {

    MYSQL(0,"mysql"),
    MONGODB(2,"mongodb")

    ;

    private Integer code;
    private String message;

    DbTypeEnums(Integer code,String message) {
        this.code = code;
        this.message = message;
    }


}
