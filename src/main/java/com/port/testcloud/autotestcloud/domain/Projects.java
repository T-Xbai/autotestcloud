package com.port.testcloud.autotestcloud.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class Projects {

  @Id
  private String id;

  private String projectName;

  private String projectDescription;

  private String createUser;

  private Integer isDelete;

  private Date createTime;

  private Date updateTime;

}
