package com.port.testcloud.autotestcloud.form;

import lombok.Data;

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

    private String dbConfigId;

    private String runSql;

    private Integer operation;

}
