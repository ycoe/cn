package com.duoec.cn.web.dto;

/**
 * Created by ycoe on 17/1/17.
 */
public class FileInfoDto {
    private String originalFileName;
    private String tempFileName;
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
}
