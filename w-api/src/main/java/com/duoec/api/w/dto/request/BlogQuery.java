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

    /**
     * 更新时间-开始(时间戳)
     */
    private Long startTime;

    /**
     * 更新时间-结束(时间戳)
     */
    private Long endTime;

    public Boolean getSeed() {
        return seed;
    }

    public void setSeed(Boolean seed) {
        this.seed = seed;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
