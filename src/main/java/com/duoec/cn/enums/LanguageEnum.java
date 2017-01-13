package com.duoec.cn.enums;

/**
 * Created by ycoe on 16/12/30.
 */
public enum LanguageEnum {
    ZH("中文"),

    EN("英文");

    private String langName;

    LanguageEnum(String langName) {
        this.langName = langName;
    }
}
