package com.port.testcloud.autotestcloud.VO;

import lombok.Data;
import org.springframework.data.domain.Pageable;

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

    private Integer offSet;

    private Integer pageSize;

    private Integer pageNumber;

    public ResultVO(Integer code, String message, T entiy) {
        this.code = code;
        this.message = message;
        this.entiy = entiy;
    }

    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVO(Integer code, String message, T entiy, Pageable pageable) {
        this.code = code;
        this.message = message;
        this.entiy = entiy;
        this.pageSize = pageable.getPageSize();
        this.offSet = (int)pageable.getOffset();
        this.pageNumber = pageable.getPageNumber();
    }

    public ResultVO() {
    }
}
