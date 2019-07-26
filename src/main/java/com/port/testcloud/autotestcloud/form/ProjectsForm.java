package com.port.testcloud.autotestcloud.form;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @ClassName: ProjectsForm
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 16:00
 * @Description: 项目表单
 */
@Data
public class ProjectsForm {



    @NotEmpty(message = "项目名称必填")
    private String projectName;

    private String projectDescription;

    private String createUser;




}
