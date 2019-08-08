package com.port.testcloud.autotestcloud.VO;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CaseInfoVO
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 18:03
 * @Description: 用例详情 -> responseBody
 */
@Data
public class CaseInfoVO {

    private String id;

    private String moduleId;

    private String caseName;

    private Integer indexs;

    private Integer isDelete;

    private String requestUrl;

    private String requestMethod;

    private String requestBody;

    private String requestHeaders;

    private String checkResult;

    private List<DependCaseVO> dependCaseList;

    private List<DbOperationVO> dbOperationList;


    private Date createTime;

    private Date updateTime;
}
