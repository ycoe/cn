package com.duoec.api.w.dto.request;

import com.duoec.web.base.dto.request.BasePaginationRequest;

/**
 * @author xuwenzhen
 */
public class BlogCommentQuery extends BasePaginationRequest {
    /**
     * 博客ID
     *
     * @reqested
     */
    private Long blogId;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
