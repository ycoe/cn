package com.duoec.api.w.dto.request;

import com.duoec.api.w.mongo.entity.BlogArticle;
import com.duoec.api.w.mongo.entity.BlogFile;
import com.duoec.api.w.mongo.entity.BlogImage;

import java.util.List;

/**
 * @author xuwenzhen
 */
public class BlogSaveRequest {
    /**
     * ID
     */
    private Long id;

    /**
     * 内容
     * @required
     */
    private String content;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 父级ID
     */
    private List<Long> parentIds;

    /**
     * 种子ID
     * @required
     */
    private Long seedId;

    /**
     * 分类IDs（包含所有的分类）
     */
    private List<Integer> cateIds;

    /**
     * 图片
     */
    private List<BlogImage> images;

    /**
     * 视频
     */
    private List<String> videos;

    /**
     * 附件
     */
    private List<BlogFile> files;

    /**
     * 博客关联文章
     */
    private List<BlogArticle> articles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }

    public Long getSeedId() {
        return seedId;
    }

    public void setSeedId(Long seedId) {
        this.seedId = seedId;
    }

    public List<Integer> getCateIds() {
        return cateIds;
    }

    public void setCateIds(List<Integer> cateIds) {
        this.cateIds = cateIds;
    }

    public List<BlogImage> getImages() {
        return images;
    }

    public void setImages(List<BlogImage> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public List<BlogFile> getFiles() {
        return files;
    }

    public void setFiles(List<BlogFile> files) {
        this.files = files;
    }

    public List<BlogArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<BlogArticle> articles) {
        this.articles = articles;
    }
}
