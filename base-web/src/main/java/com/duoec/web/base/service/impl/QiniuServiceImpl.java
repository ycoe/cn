package com.duoec.web.base.service.impl;

import com.duoec.web.base.service.QiniuService;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;
import org.json.JSONException;
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
        Mac mac = new Mac(accessKey, secretKey);
        // 请确保该bucket已经存在
        PutPolicy putPolicy = new PutPolicy(bucketName);
        try {
            return putPolicy.token(mac);
        } catch (AuthException e) {
            logger.error("七牛鉴权失败", e);
        } catch (JSONException e) {
            logger.error("获取七牛上传token失败", e);
        }
        return null;
    }
}
