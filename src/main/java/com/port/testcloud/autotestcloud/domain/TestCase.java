package com.port.testcloud.autotestcloud.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 测试用例
 */

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class TestCase {

  @Id
  private String id;

  private String moduleId;

  private String caseName;

  private Integer indexs;

  private Integer isDelete;

  private Date createTime;

  private Date updateTime;
}
