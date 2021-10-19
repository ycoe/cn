package com.duoec.api.w.dto.response;

import com.duoec.api.w.mongo.entity.Blog;

/**
 *
 * @author xuwenzhen
 */
public class BlogNewVo extends Blog {
    /**
     * 父级微博，一般在回复中存在
     */
    private Blog parent;

    public Blog getParent() {
        return parent;
    }

    public void setParent(Blog parent) {
        this.parent = parent;
    }
}
