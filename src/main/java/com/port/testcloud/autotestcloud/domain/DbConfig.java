package com.port.testcloud.autotestcloud.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class DbConfig {

  @Id
  private String id;

  private Integer dbType;

  private String name;

  private String host;

  private String username;

  private String password;

  private String databaseName;

  private String port;




}
