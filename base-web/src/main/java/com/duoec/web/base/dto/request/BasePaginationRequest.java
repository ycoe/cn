package com.duoec.web.base.dto.request;

/**
 * 带分页的查询
 *
 * @author xuwenzhen
 */
public class BasePaginationRequest {
    /**
     * 当前分页数，1表示第一页
     *
     * @required
     * @demo 1
     */
    private Integer pageNo;

    /**
     * 每页最大记录数
     *
     * @required
     * @demo 20
     */
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
