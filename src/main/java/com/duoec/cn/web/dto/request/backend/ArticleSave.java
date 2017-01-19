package com.duoec.cn.web.dto.request.backend;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
public class ArticleSave {
    /**
     * ID，0表示新增
     */
    private long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 概要
     */
    private String summary;

    /**
     * 当前语言
     */
    private String language;

    /**
     * 分类，支持多个分类！
     */
    private List<Long> cateIds;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Long> getCateIds() {
        return cateIds;
    }

    public void setCateIds(List<Long> cateIds) {
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
