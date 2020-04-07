package com.duoec.api.w.dto.response;

import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

/**
 * 用户基本信息
 *
 * @author xuwenzhen
 */
public class UserInfo {
    /**
     * 用户ID
     */
    @AutoIncrement
    private Integer id;

    /**
     * 登录名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 博客数量
     */
    private Integer blogCount;

    /**
     * 博客评论数量
     */
    private Integer blogCommentCount;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 上次登录时间
     */
    private Long lastLoginTime;

    /**
     * 用户授权码
     */
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getBlogCommentCount() {
        return blogCommentCount;
    }

    public void setBlogCommentCount(Integer blogCommentCount) {
        this.blogCommentCount = blogCommentCount;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
