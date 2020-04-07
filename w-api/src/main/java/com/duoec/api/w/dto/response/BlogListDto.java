package com.duoec.api.w.dto.response;

import com.duoec.api.w.mongo.entity.Blog;

import java.util.List;

/**
 * @author xuwenzhen
 */
public class BlogListDto {
    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 当前分页的微博列表
     */
    private List<Blog> list;

    /**
     * 相关的用户信息
     */
    private List<UserInfo> users;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Blog> getList() {
        return list;
    }

    public void setList(List<Blog> list) {
        this.list = list;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
}
