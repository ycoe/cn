package com.duoec.api.w.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("action")
public class ActionEntity {
    private Integer id;

    private Date actionDate;

    private String actionTypeId;

    private Integer userId;

    private String result;

    private Byte score;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(String actionTypeId) {
        this.actionTypeId = actionTypeId == null ? null : actionTypeId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Byte getScore() {
        return score;
    }

    public void setScore(Byte score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}