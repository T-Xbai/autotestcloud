package com.port.testcloud.autotestcloud.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DbOperation {

  @Id
  private String id;

  private String caseId;

  private String dbConfigId;

  private String sql;

  private Integer operation;




}
