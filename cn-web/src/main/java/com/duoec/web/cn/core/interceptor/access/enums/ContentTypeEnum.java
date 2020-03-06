package com.duoec.web.cn.core.interceptor.access.enums;

/**
 * Created by ycoe on 17/1/5.
 */
public enum ContentTypeEnum {
    TEXT_HTML("text/html;charset=UTF-8"),

    APPLICATION_JSON("application/json;charset=UTF-8");

    private String value;

    ContentTypeEnum(String value) {
        this.value = value;
    }
}
