package com.duoec.api.w.mongo.entity;

import java.util.List;

/**
 * 博客关联图片
 *
 * @author xuwenzhen
 */
public class BlogImage {
    /**
     * 图片地址
     */
    private String url;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private List<String> tags;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
