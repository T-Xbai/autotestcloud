package com.port.testcloud.autotestcloud.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: DbOperationForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 11:28
 * @Description: 数据库操作
 */
@Data
public class DbOperationForm {

    private String id;

    private String caseId;

    @NotEmpty(message = "dbConfigId 必传")
    private String dbConfigId;

    private String runSql;

    private Integer operation = 0;

    private String dependVariable;

}
