package com.duoec.api.w.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("category")
public class CategoryEntity {
    private Integer id;

    private String name;

    private String remark;

    private Integer parentId;

    private String type;

    private Byte available;

    private Byte typeValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Byte getAvailable() {
        return available;
    }

    public void setAvailable(Byte available) {
        this.available = available;
    }

    public Byte getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(Byte typeValue) {
        this.typeValue = typeValue;
    }
}