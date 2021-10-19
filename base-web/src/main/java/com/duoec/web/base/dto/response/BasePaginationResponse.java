package com.duoec.web.base.dto.response;

import java.util.List;

/**
 * 带分页的数据列表
 *
 * @author xuwenzhen
 */
public class BasePaginationResponse<T> {
    /**
     * 总记录数
     *
     * @demo 1024
     */
    private Integer total;

    /**
     * 数据列表（带分页）
     */
    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
