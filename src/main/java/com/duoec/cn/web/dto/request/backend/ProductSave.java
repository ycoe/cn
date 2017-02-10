package com.duoec.cn.web.dto.request.backend;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
public class ProductSave {
    /**
     * ID，0表示新增
     */
    private long id;

    /**
     * 标题
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 编号
     */
    private String num;

    /**
     * 规格
     */
    private String spec;

    /**
     * 概要，主要用于首页展示
     */
    private String summary;

    /**
     * 当前语言
     */
    private String language;

    /**
     * 分类，支持多个分类！
     */
    private String cateIds;

    /**
     * 标识
     */
    private List<String> flags;

    /**
     * 图片URL
     */
    private String coverImage;

    /**
     * 内容
     */
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCateIds() {
        return cateIds;
    }

    public void setCateIds(String cateIds) {
        this.cateIds = cateIds;
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
