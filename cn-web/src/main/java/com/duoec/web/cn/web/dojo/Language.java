package com.duoec.web.cn.web.dojo;

/**
 * Created by ycoe on 17/1/4.
 */
public class Language {
    /**
     * ID，即语言名称：com.duoec.web.cn / en / ...
     */
    private String id;

    /**
     * 名称：中文 / 英文 / ...
     */
    private String name;

    /**
     * 更新时间
     */
    private long updateTime;

    /**
     * 状态： -1已删除 0正常 1默认
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
