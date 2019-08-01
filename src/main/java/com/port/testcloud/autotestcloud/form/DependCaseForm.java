package com.port.testcloud.autotestcloud.form;

import lombok.Data;

/**
 * @ClassName: DependCaseForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-01 14:50
 * @Description: 依赖case 请求表单
 */

@Data
public class DependCaseForm {

    private String id;

    private String dependCase;

    private String dependParams;

    private Integer operation = 0;

}
