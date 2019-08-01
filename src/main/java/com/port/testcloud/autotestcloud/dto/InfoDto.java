package com.port.testcloud.autotestcloud.dto;

import lombok.Data;

/**
 * @ClassName: InfoDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-31 17:09
 * @Description: 用例详情
 */
@Data
public class InfoDto {

    private String id;

    private String caseId;

    private String requestUrl;

    private String requestMethod;

    private String requestBody;

    private String requestHeaders;

    private String checkResult;

}
