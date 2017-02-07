package com.duoec.cn.enums;

/**
 * Created by ycoe on 17/1/19.
 */
public enum ProductFlagEnum {
    Hot("热卖"),
    Recommend("推荐"),
    Index("首页")
    ;

    private String text;

    ProductFlagEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static ProductFlagEnum getByName(String name) {
        try {
            return ProductFlagEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
