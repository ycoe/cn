package com.duoec.cn.core.dto;

import com.alibaba.fastjson.JSON;
import com.fangdd.traffic.common.mongodb.AutoIncrement;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/8/1.
 */
public class TreeNode {
    /**
     * 主键
     */
    @AutoIncrement
    private long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 各语言名称
     */
    private Map<String, String> names;

    /**
     * 子级
     */
    private List<TreeNode> children;

    /**
     * 排序,越小越前
     */
    private int sort;

    /**
     * 状态码： -1已删除 >=0正常
     */
    private int status = 0;

    /**
     * 更新时间
     */
    private long updateTime;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Map<String, String> getNames() {
        return names;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }

    public void add(TreeNode node) {
        if (this.children == null) {
            this.children = Lists.newArrayList();
        }
        if (!this.children.contains(node)) {
            //两次追加时，对象可能不同，需要将旧的对象删除
            this.children.remove(node);
        }
        this.children.add(node);
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public List<? extends TreeNode> getChildren() {
        return children;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 重写比较方法，如果两者ID相同，则为相同
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        TreeNode o = (TreeNode) obj;
        return o.getId() == this.getId();
    }
}
