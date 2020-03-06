package com.duoec.web.cn.web.dto.request.backend;

import java.util.Map;

/**
 * Created by ycoe on 17/1/11.
 */
public class CategorySave {
    /**
     * ID，为空表示新增
     */
    private Long id;

    /**
     * 各语言的名称
     */
    private Map<String, String> names;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 类型
     */
    private int type;

    /**
     * 可视状态
     */
    private Integer visible;

    /**
     * 有效状态
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
