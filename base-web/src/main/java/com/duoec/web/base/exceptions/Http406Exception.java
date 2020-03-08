package com.duoec.web.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Acceptable
 * Created by ycoe on 16/5/13.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class Http406Exception extends RuntimeException {
    /**
     * 登录链接
     */
    private String loginUrl;

    public Http406Exception(String message, String longinUrl) {
        super(message);
        this.loginUrl = longinUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }
}
