package com.port.testcloud.autotestcloud.dto;

import com.port.testcloud.autotestcloud.domain.DependCase;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: TestCaseDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-29 23:46
 * @Description:
 */
@Data
public class TestCaseDto {

    private String id;

    private String moduleId;

    private String caseName;

    private Integer indexs;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    private InfoDto info;

    private List<DependCase> dependCaseList;
}
