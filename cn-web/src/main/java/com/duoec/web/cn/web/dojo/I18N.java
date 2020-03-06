package com.duoec.web.cn.web.dojo;

import java.util.Map;

/**
 * Created by ycoe on 16/12/30.
 */
public class I18N {
    /**
     * ID {代码}
     */
    private String id;

    /**
     * 描述
     */
    private String desc;

    /**
     * 语言
     */
    private Map<String, String> values;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 状态： -1已删除 1有效
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
