package com.duoec.cn.enums;

import com.google.common.base.Strings;

/**
 * Created by ycoe on 17/1/11.
 */
public enum CategoryTypeEnum {
    /**
     * 新闻、资讯
     */
    NEWS(0, "新闻"),

    /**
     * 产品
     */
    PRODUCT(1, "产品"),

    /**
     * 相册
     */
    ALBUM(2, "相册");

    /**
     * 类型值
     */
    private int type;

    /**
     * 类型名称
     */
    private String typeName;

    CategoryTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getType() {
        return type;
    }

    /**
     * 判断某个值是否是有效的分类类型值
     *
     * @param type
     * @return
     */
    public static boolean available(int type) {
        return getByType(type) != null;
    }

    /**
     * 通过枚举名称判断是否是有效的
     *
     * @param type
     * @return
     */
    public static boolean available(String type) {
        return getByType(type) != null;
    }

    public static CategoryTypeEnum getByType(String type) {
        if (Strings.isNullOrEmpty(type)) {
            return null;
        }
        String enumName = type.toUpperCase();
        for (CategoryTypeEnum categoryTypeEnum : CategoryTypeEnum.values()) {
            if (categoryTypeEnum.name().equals(enumName)) {
                return categoryTypeEnum;
            }
        }
        return null;
    }

    public static CategoryTypeEnum getByType(int type) {
        for (CategoryTypeEnum categoryTypeEnum : CategoryTypeEnum.values()) {
            if (categoryTypeEnum.getType() == type) {
                return categoryTypeEnum;
            }
        }
        return null;
    }
}
