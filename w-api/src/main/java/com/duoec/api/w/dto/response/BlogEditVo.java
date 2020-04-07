package com.duoec.api.w.dto.response;

import com.duoec.api.w.mongo.entity.Blog;

/**
 * @author xuwenzhen
 */
public class BlogEditVo {
    /**
     * 微博信息详情
     */
    private Blog blog;

    /**
     * 用于上传的token
     */
    private String token;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
