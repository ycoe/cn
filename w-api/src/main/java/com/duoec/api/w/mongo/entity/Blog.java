package com.duoec.api.w.mongo.entity;

import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

import java.util.List;

/**
 * 博客
 *
 * @author xuwenzhen
 */
public class Blog {
    public static final String FIELD_CREATE_TIME = "createTime";
    public static final String FIELD_UPDATE_TIME = "updateTime";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_PARENT_IDS = "parentIds";
    public static final String FIELD_COMMENT_COUNT = "commentCount";
    public static final String FIELD_SEED_ID = "seedId";
    public static final String FIELD_DELETE = "delete";

    /**
     * ID
     */
    @AutoIncrement
    private Long id;

    /**
     * 内容
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
     */
    private Long seedId;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 回复
     */
    private List<Blog> comments;

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

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 作者ID
     */
    private Integer userId;

    /**
     * 是否已删除
     */
    private Boolean delete;

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

    public List<BlogArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<BlogArticle> articles) {
        this.articles = articles;
    }

    public Long getSeedId() {
        return seedId;
    }

    public void setSeedId(Long seedId) {
        this.seedId = seedId;
    }

    public List<Blog> getComments() {
        return comments;
    }

    public void setComments(List<Blog> comments) {
        this.comments = comments;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
