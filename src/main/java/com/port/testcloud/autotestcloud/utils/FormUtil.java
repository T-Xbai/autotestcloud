package com.port.testcloud.autotestcloud.utils;

import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

/**
 * @ClassName: FormUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-05 15:33
 * @Description:
 */
@Slf4j
public class FormUtil {

    public static void hasError(BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            log.error("请求参数错误：{}", errorMsg);
            throw new AutoTestException(ResultEnums.FORM_PARAM_ERROR);
        }
    }

}
