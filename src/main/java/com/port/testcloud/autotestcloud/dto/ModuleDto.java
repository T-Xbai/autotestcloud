package com.port.testcloud.autotestcloud.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ModuleDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 18:14
 * @Description: 模块 dto
 */
@Data
public class ModuleDto {

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

    /* 子集 */
    List<ModuleDto> sonList;

    private Date createTime;

    private Date updateTime;
}
