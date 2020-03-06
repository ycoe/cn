package com.duoec.web.cn.core.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ycoe on 16/5/13.
 */
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class Http405Exception extends RuntimeException {
    private String message;

    private String url;

    public Http405Exception(String message, String url) {
        super(message);
        this.message = message;
        this.url = url;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}
