package com.port.testcloud.autotestcloud.utils;

import com.port.testcloud.autotestcloud.dto.DataVariableDto;

import java.util.List;

/**
 * @ClassName: ReplaceUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-09 13:54
 * @Description: 替换变量值
 */
public class ReplaceUtil {


    public static String all(List<DataVariableDto> variables, String replaceStr) {

        if (variables != null && variables.size() > 0) {
            for (DataVariableDto variable : variables) {
                String patternValue = "\\{\\{ ".concat(variable.getKey()).concat(" \\}\\}");
                replaceStr = replaceStr.replaceAll(patternValue, variable.getValue());
            }
        }
        return replaceStr;
    }


}
