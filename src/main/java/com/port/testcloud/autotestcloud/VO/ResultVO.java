package com.port.testcloud.autotestcloud.VO;

import lombok.Data;

/**
 * @ClassName: ResultVO
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 10:31
 * @Description: 统一响应结果
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String message;

    private T entiy;

    public ResultVO(Integer code, String message, T entiy) {
        this.code = code;
        this.message = message;
        this.entiy = entiy;
    }


    public ResultVO() {
    }
}
