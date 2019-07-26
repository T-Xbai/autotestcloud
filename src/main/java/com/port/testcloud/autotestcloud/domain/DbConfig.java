package com.port.testcloud.autotestcloud.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DbConfig {

  @Id
  private String id;

  private String dbType;

  private String name;

  private String host;

  private String username;

  private String password;

  private String database;

  private String port;




}
