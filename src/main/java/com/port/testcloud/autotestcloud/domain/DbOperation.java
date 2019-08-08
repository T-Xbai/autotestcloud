package com.port.testcloud.autotestcloud.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class DbOperation {

  @Id
  private String id;

  private String caseId;

  private String dbConfigId;

  private String runSql;

  private Integer operation;




}
