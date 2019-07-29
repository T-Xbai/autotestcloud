package com.port.testcloud.autotestcloud.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class ProjectModules {

  @Id
  private String id;

  /* 项目id */
  private String projectId;

  /* 父级模块 */
  private String parentId;

  /* 模块排序 */
  private Integer indexs;

  /* 模块名称 */
  private String moduleName;

  /* 模块介绍 */
  private String moduleDescription;

  /* 删除状态 */
  private Integer isDelete;


  private Date createTime;

  private Date updateTime;



}
