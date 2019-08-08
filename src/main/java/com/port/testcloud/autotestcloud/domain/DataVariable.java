package com.port.testcloud.autotestcloud.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @ClassName: DataVariable
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-08 23:23
 * @Description: 数据变量
 */
@Entity
@Data
public class DataVariable {

    private String id;

    private String projectId;

    private String vKey;

    private String vValue;

    private String description;

}
