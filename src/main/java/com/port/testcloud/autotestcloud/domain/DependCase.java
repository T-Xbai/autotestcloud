package com.port.testcloud.autotestcloud.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DependCase {

  @Id
  private String id;

  private String caseId;

  private String dependCase;

  private String dependParams;

  private Integer operation;
}
