package com.port.testcloud.autotestcloud.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class TestCase {

  @Id
  private String id;

  private String moduleId;

  private String caseName;

  private Integer index;

  private Integer isDelete;

  private Date createTime;

  private Date updateTime;
}
