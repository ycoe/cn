package com.duoec.cn.web.dojo;

/**
 * Created by ycoe on 16/12/30.
 */
public class I18N {
    /**
     * ID
     */
    private String id;

    /**
     * 代码
     */
    private String code;

    /**
     * 语言
     */
    private String language;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 状态： -1已删除 1有效
     */
    private int status;

    /**
     * 描述
     */
    private String desc;

    /**
     * 值
     */
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
