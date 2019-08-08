package com.port.testcloud.autotestcloud.utils;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.dto.TestCaseDto;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import org.springframework.data.domain.Page;

/**
 * @ClassName: ResultVOUtil
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 10:38
 * @Description: 统一响应实体 - 工具类
 */
public class ResultVOUtil {

    public static ResultVO<Object> success(Object object) {
        return new ResultVO<>(
                ResultEnums.SUCCESS.getCode(),
                ResultEnums.SUCCESS.getMes(),
                object
        );
    }

    public static ResultVO<Object> success() {
        return success(null);
    }

    public static ResultVO<Object> success(Object object , Page page) {
        return new ResultVO<>(
                ResultEnums.SUCCESS.getCode(),
                ResultEnums.SUCCESS.getMes(),
                object,
                page.getPageable()
        );
    }


    public static ResultVO error(Integer code, String message) {
        return new ResultVO(code, message);
    }

    public static ResultVO<Object> body(ResultEnums resultEnums, Object object) {
        return new ResultVO<>(
                resultEnums.getCode(),
                resultEnums.getMes(),
                object
        );
    }


}
