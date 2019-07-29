package com.port.testcloud.autotestcloud.enums;

import lombok.Getter;

/**
 * @ClassName: ResultEnums
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 10:23
 * @Description:
 */
@Getter
public enum ResultEnums {

    SUCCESS(0, "成功"),

    // 项目相关
    PROJECT_NAME_IS_EXIST(5001, "项目名称已存在"),
    PROJECT_ID_NOT_EXIST(5002, "projectId 不存在"),
    PROJECT_FORM_ERROR(5003, "传入表单参数错误"),
    PROJECT_ERROR(5004,"项目异常"),



    // 模块相关
    PARENT_MODULE_NOT_EXIST(5101, "父模块不存在"),
    MODULE_NOT_EXIST(5102, "模块不存在"),
    MODULE_INDEX_NOT_REPETITION(5103, "一个模块下的index值不可重复"),
    MODULE_ERROR(5104,"模块异常"),
    PROJECT_ID_NOT_EQUALITY(5105,"项目id不一致"),

    FORM_PARAM_ERROR(5200,"请求参数错误"),


    ;

    private Integer code;

    private String mes;

    ResultEnums(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }
}
