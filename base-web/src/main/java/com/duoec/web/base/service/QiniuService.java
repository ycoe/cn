package com.duoec.web.base.service;

/**
 * @author xuwenzhen
 */
public interface QiniuService {
    /**
     * 获取上传token
     * @param bucketName bucket name
     * @return
     */
    String getUploadToken(String bucketName);
}
