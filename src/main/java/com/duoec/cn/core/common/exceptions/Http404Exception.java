package com.duoec.cn.core.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ycoe on 16/5/13.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "页面不存在!")
public class Http404Exception extends RuntimeException {
    public Http404Exception(String message) {
        super(message);
    }
}
