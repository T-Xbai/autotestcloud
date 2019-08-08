package com.port.testcloud.autotestcloud.dto;

import lombok.Data;

/**
 * @ClassName: AssertResultDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-07 17:06
 * @Description: 校验结果
 */
@Data
public class AssertResultDto {

    private boolean status ;

    private Object expect;


    private Object practical;
}
