package com.duoec.cn.core.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ycoe on 16/5/12.
 */
@ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)
public class Http301Exception extends RuntimeException {
    private final String redirectUrl;

    public Http301Exception(String redirectUri) {
        this.redirectUrl = redirectUri;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
