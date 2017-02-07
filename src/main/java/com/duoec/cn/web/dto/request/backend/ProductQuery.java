package com.duoec.cn.web.dto.request.backend;

import com.duoec.cn.enums.ProductFlagEnum;

/**
 * Created by ycoe on 17/1/14.
 */
public class ProductQuery {
    /**
     * 语言
     */
    private String lang;

    /**
     * 父级ID，-1表示不限制
     */
    private long parentId = -1;

    /**
     * 搜索关键词，目前只搜索标题
     */
    private String keyword;

    /**
     * 文章状态
     */
    private Integer status;

    /**
     * 标识
     */
    private String flag;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
