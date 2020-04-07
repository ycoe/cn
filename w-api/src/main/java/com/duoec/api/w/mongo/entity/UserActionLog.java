package com.duoec.api.w.mongo.entity;

import com.duoec.api.w.dto.enums.UserActionEnum;
import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

/**
 * 用户事件
 * @author xuwenzhen
 */
public class UserActionLog {
    /**
     * ID
     */
    @AutoIncrement
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 事件
     * @see UserActionEnum
     */
    private Integer action;

    /**
     * 事件标题
     */
    private String title;

    /**
     * 事件时间
     */
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
