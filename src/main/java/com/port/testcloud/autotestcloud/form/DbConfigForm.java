package com.port.testcloud.autotestcloud.form;

import lombok.Data;


/**
 * @ClassName: DbConfigForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-05 15:25
 * @Description:
 */
@Data
public class DbConfigForm {

    private Integer dbType = 0;

    private String name;

    private String host;

    private String username;

    private String password;

    private String databaseName;

    private String port;

}
