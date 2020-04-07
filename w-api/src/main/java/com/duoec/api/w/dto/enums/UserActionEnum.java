package com.duoec.api.w.dto.enums;

/**
 * 用户事件
 *
 * @author xuwenzhen
 */
public enum UserActionEnum {
    /**
     * 登录
     */
    LOGIN(0),

    /**
     * 微博创建
     */
    BLOG_CREATE(10),

    /**
     * 微博修改
     */
    BLOG_MODIFY(11),

    /**
     * 微博删除
     */
    BLOG_DELETE(12),

    /**
     * 微博评论创建
     */
    BLOG_COMMENT_CREATE(13),

    /**
     * 微博评论修改
     */
    BLOG_COMMENT_MODIFY(14),

    /**
     * 微博评论删除
     */
    BLOG_COMMENT_DELETE(15),

    ;
    private Integer action;

    UserActionEnum(Integer action) {
        this.action = action;
    }

    public Integer getAction() {
        return action;
    }
}
