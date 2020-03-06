package com.duoec.web.cn.core.dto;

/**
 * 页面缓存,本缓存实现逻辑: 不设置过期时间,而是设置过期状态,页面更新时间太久时,会自动设置成待更新
 */
public class PageCache {
    /**
     * ID
     */
    private String id;

    /**
     * 内容
     */
    private String text;

    /**
     * 创建时间
     */
    private long updateTime;

    /**
     * 状态: 0正常 -1已过期
     */
    private int status = 0;

    /**
     * 类型: 0页面 1数据
     */
    private int type = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
