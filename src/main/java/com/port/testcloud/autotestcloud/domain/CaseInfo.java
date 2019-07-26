package com.port.testcloud.autotestcloud.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CaseInfo {

    @Id
    private String id;

    private String caseId;

    private String requestUrl;

    private String requestMethod;

    private String requestBody;

    private String requestHeaders;

    private String checkResult;
}
