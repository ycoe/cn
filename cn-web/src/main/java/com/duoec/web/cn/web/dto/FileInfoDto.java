package com.duoec.web.cn.web.dto;

/**
 * Created by ycoe on 17/1/17.
 */
public class FileInfoDto {
    /**
     * 原始图片名称
     */
    private String originalFileName;

    /**
     * 临时图片名称
     */
    private String tempFileName;

    /**
     * 最终图片URL（全路径！）
     */
    private String url;

    /**
     * 图片大小
     */
    private long size;

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
