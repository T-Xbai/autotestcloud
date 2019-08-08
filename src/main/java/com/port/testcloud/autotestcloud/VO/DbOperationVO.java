package com.port.testcloud.autotestcloud.VO;

import lombok.Data;

/**
 * @ClassName: DbOperationVO
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-02 11:55
 * @Description:
 */
@Data
public class DbOperationVO {

    private String id;

    private String caseId;

    private String dbConfigId;

    private String runSql;

    private Integer operation;


}
