package com.duoec.web.cn.web.dojo;

import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
public class Article {
    /**
     * ID
     */
    @AutoIncrement
    private long id;

    /**
     * 可以通过此值进行调用，每种语言唯一！
     */
    private String code;

    /**
     * 标题
     */
    private String title;

    /**
     * 概要
     */
    private String summary;

    /**
     * 内容
     */
    private String content;

    /**
     * 语言ID
     */
    private String language;

    /**
     * 封面图
     */
    private String coverImage;

    /**
     * 所属分类，可同属于多个分类
     */
    private List<Long> parentIds;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 状态： -1已删除 1正常
     */
    private int status;

    /**
     * 标识：com.duoec.web.cn.enums.ArticleFlagEnum
     */
    private List<String> flags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public List<String> getFlags() {
        return flags;
    }
}
