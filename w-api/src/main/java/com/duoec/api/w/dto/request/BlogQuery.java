package com.duoec.api.w.dto.request;

import com.duoec.web.base.dto.request.BasePaginationRequest;

/**
 * 查询
 *
 * @author xuwenzhen
 */
public class BlogQuery extends BasePaginationRequest {
    /**
     * 是否只加载根微博（不包含评论）
     */
    private Boolean seed;

    public Boolean getSeed() {
        return seed;
    }

    public void setSeed(Boolean seed) {
        this.seed = seed;
    }
}
