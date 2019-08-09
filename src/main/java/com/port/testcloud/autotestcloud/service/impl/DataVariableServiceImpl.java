package com.port.testcloud.autotestcloud.service.impl;

import com.google.gson.reflect.TypeToken;
import com.port.testcloud.autotestcloud.dto.DataVariableDto;
import com.port.testcloud.autotestcloud.service.DataVariableService;
import com.port.testcloud.autotestcloud.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 抛弃 ： DataVariableServiceImpl
 *
 * @ClassName: DataVariableServiceImpl
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-09 09:47
 * @Description:
 */

@Service
@Slf4j

public class DataVariableServiceImpl implements DataVariableService {


    @Override
    public List<DataVariableDto> get(String dataVariable) {

        Type variableType = new TypeToken<ArrayList<DataVariableDto>>() {
        }.getType();
        List<DataVariableDto> dataVariableDtoList = (List<DataVariableDto>) JsonUtil.fromJson(dataVariable, variableType);
        return dataVariableDtoList != null ? dataVariableDtoList : new ArrayList<>() ;
    }
}
