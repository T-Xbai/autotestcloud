package com.port.testcloud.autotestcloud.handle;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ClassName: AutoCloudException
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-30 14:43
 * @Description: 项目异常统一捕获
 */
@ControllerAdvice
public class AutoCloudException {


    @ExceptionHandler(value = AutoTestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResultVO<String> autoCloudException(AutoTestException e)  {
        return new ResultVO<>(e.getCode(), e.getMessage());
    }

}
