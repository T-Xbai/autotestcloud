package com.port.testcloud.autotestcloud.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class RunResult {

  @Id
  private String id;

  private String caseId;

  private Date runTime;

  private String requestUrl;

  private String requestMethod;

  private String requestHeaders;

  private String requestBody;

  private Integer statusCode;

  private String responseBody;

  private String exceptionMessage;

  private String checkResult;

  public RunResult() {

  }


}
