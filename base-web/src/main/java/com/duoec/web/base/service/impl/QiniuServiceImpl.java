package com.duoec.web.base.service.impl;

import com.duoec.web.base.service.QiniuService;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xuwenzhen
 */
@Service
public class QiniuServiceImpl implements QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuServiceImpl.class);

    @Value("${qiniu.access_key}")
    private String accessKey;

    @Value("${qiniu.secret_key}")
    private String secretKey;

    /**
     * 获取上传token
     *
     * @param bucketName bucket name
     * @return upload token
     */
    @Override
    public String getUploadToken(String bucketName) {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucketName);
    }
}
