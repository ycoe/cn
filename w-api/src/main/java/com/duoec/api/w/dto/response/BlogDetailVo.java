package com.duoec.api.w.dto.response;

import com.duoec.api.w.mongo.entity.Blog;

import java.util.List;

/**
 * @author xuwenzhen
 */
public class BlogDetailVo {
    /**
     * 微博信息详情
     */
    private Blog blog;

    /**
     * 相关的用户列表
     */
    private List<UserInfo> userInfoList;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
