package com.duoec.api.w.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("action_type_config")
public class ActionTypeConfigEntity {
    private Integer id;

    private String name;

    private String remark;

    private String options;

    private Byte multiple;

    private Byte withWho;

    private Byte scope;

    private Integer userId;

    private Byte singleInDay;

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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
    }

    public Byte getMultiple() {
        return multiple;
    }

    public void setMultiple(Byte multiple) {
        this.multiple = multiple;
    }

    public Byte getWithWho() {
        return withWho;
    }

    public void setWithWho(Byte withWho) {
        this.withWho = withWho;
    }

    public Byte getScope() {
        return scope;
    }

    public void setScope(Byte scope) {
        this.scope = scope;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getSingleInDay() {
        return singleInDay;
    }

    public void setSingleInDay(Byte singleInDay) {
        this.singleInDay = singleInDay;
    }
}