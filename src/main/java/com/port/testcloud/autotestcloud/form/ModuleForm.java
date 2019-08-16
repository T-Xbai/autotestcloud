package com.port.testcloud.autotestcloud.form;

import com.port.testcloud.autotestcloud.utils.JsonUtil;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName: ModuleForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-28 21:35
 * @Description:
 */
@Data
public class ModuleForm {

    /* 项目id */
    @NotEmpty(message = "项目Id必传")
    private String projectId;

    /* 父级模块 */
    private String parentId;

    /* 模块排序 */
    private Integer indexs;

    /* 模块名称 */
    @NotEmpty(message = "模板名称必传")
    private String moduleName;

    /* 模块介绍 */
    private String moduleDescription;


    private Date createTime;

    private Date updateTime;

    public static void main(String[] args) {
        ModuleForm moduleForm = new ModuleForm();
        System.out.println(JsonUtil.toJson(moduleForm));
    }
}


