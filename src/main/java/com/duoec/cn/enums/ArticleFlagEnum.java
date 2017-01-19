package com.duoec.cn.enums;

/**
 * Created by ycoe on 17/1/19.
 */
public enum ArticleFlagEnum {
    Hot("热门"),
    Recommend("推荐"),;

    private String text;

    ArticleFlagEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
