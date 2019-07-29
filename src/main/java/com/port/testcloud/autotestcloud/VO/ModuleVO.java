package com.port.testcloud.autotestcloud.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: ModuleVO
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-28 22:33
 * @Description:
 */
@Data
public class ModuleVO {


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
    @JsonIgnore
    private Integer isDelete;

    /* 子集 */
    List<ModuleDto> sonList;

    private Date createTime;

    private Date updateTime;

}
