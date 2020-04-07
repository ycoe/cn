package com.duoec.api.w.mongo.entity;

/**
 * 博客文件
 * @author xuwenzhen
 */
public class BlogFile {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件大小
     */
    private Long size;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
