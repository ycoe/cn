package com.duoec.web.cn.web.dto.request.backend;

import com.duoec.web.cn.enums.LanguageEnum;

/**
 * Created by ycoe on 16/12/30.
 */
public class I18NQuery {
    /**
     * 语言
     */
    private LanguageEnum lang;

    /**
     * 搜索词
     */
    private String keyword;

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
