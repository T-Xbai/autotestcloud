package com.port.testcloud.autotestcloud.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class BuildTime {

  @Id
  private String id;

  private String projectId;

  private String timeUnit;

  private Date firstRunTime;

}
