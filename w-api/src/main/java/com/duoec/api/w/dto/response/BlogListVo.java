package com.duoec.api.w.dto.response;

import com.duoec.api.w.mongo.entity.Blog;
import com.duoec.web.base.dto.response.BasePaginationResponse;

import java.util.List;

/**
 * @author xuwenzhen
 */
public class BlogListVo extends BasePaginationResponse<Blog> {
    /**
     * 相关的用户列表
     */
    private List<UserInfo> userInfoList;

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
