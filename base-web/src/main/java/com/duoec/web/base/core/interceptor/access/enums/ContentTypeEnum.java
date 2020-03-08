package com.duoec.web.base.core.interceptor.access.enums;

/**
 *
 * @author ycoe
 * @date 17/1/5
 */
public enum ContentTypeEnum {
    TEXT_HTML("text/html;charset=UTF-8"),

    APPLICATION_JSON("application/json;charset=UTF-8");

    private String contentType;

    ContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
