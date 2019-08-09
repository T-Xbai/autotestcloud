package com.port.testcloud.autotestcloud.service;

import com.port.testcloud.autotestcloud.dto.DataVariableDto;

import java.util.List;

/**
 * @ClassName: DataVariableService
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-09 09:45
 * @Description:
 */
public interface DataVariableService {


    List<DataVariableDto> get(String dataVariable);

}
