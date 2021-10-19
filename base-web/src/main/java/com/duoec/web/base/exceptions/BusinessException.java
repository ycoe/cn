package com.duoec.web.base.exceptions;

/**
 *
 * @author ycoe
 * @date 16/12/21
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code = 500;

    private String message;

    public BusinessException(String message, Exception e) {
        super(message, e);
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
