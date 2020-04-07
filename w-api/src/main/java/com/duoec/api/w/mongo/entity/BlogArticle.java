package com.duoec.api.w.mongo.entity;

import java.util.List;

/**
 * 博客关联文章
 *
 * @author xuwenzhen
 */
public class BlogArticle {
    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面图
     */
    private List<String> images;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
