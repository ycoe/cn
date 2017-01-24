package com.duoec.cn.core.common.exceptions;

/**
 * Created by ycoe on 16/12/21.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message, Exception e) {
        super(message, e);
    }

    public BusinessException(String message) {
        super(message);
    }
}
