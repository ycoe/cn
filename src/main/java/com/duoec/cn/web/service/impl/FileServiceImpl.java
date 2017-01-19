package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.utils.UUIDUtils;
import com.duoec.cn.web.dto.FileInfoDto;
import com.duoec.cn.web.service.FileService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by ycoe on 17/1/17.
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${qiniu.access_key}")
    private String accessKey;

    @Value("${qiniu.secret_key}")
    private String secretKey;

    /**
     * 要上传的空间
     */
    @Value("${qiniu.bucket}")
    String bucketname;

    private Auth auth;
    private Zone z;
    private Configuration c;

    //创建上传对象
    private UploadManager uploadManager;

    private UploadManager getUploadManager() {
        if(uploadManager == null) {
            auth = Auth.create(accessKey, secretKey);
            z = Zone.autoZone();
            c = new Configuration(z);
            uploadManager = new UploadManager(c);
        }

        return uploadManager;
    }

    @Override
    public FileInfoDto upload(MultipartFile file) {
        FileInfoDto info = new FileInfoDto();
        String fileName = file.getOriginalFilename(); //原始名称
        info.setOriginalFileName(fileName);
        String fileType = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        String newFileName = UUIDUtils.generateUUID() + fileType;

        //临时上传目录
        String tempUploadPath = CommonConst.STATIC_PATH + "tmp/";

        File tempUploadDir = new File(tempUploadPath);
        try {
            FileUtils.forceMkdir(tempUploadDir); //创建目录
        } catch (IOException e) {
            logger.error("创建目录失败：{}", tempUploadPath, e);
            tempUploadPath = CommonConst.STATIC_PATH;
        }
        String newFilePath = tempUploadPath + "/" + newFileName;
        File newFile = new File(newFilePath);
        try {
            InputStream is = file.getInputStream();

            OutputStream os = new FileOutputStream(newFilePath);
            StreamUtils.copy(is, os);
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            logger.error("上传失败!", e);
        }

        info.setTempFileName(newFileName);
        info.setSize(file.getSize());

        //上传到七牛服务器
        uploadToQiniu(newFile);

        return info;
    }

    /**
     * 覆盖上传
     *
     * @param fileName
     * @return
     */
    private String getUpToken(String fileName) {
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        //如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        //第三个参数是token的过期时间
        return auth.uploadToken(bucketname, fileName, 3600, new StringMap().put("insertOnly", 1));
    }

    private void uploadToQiniu(File file) {
        UploadManager uploadManager = getUploadManager();
        try {
            //调用put方法上传
            Response res = uploadManager.put(file.getAbsolutePath(), file.getName(), getUpToken(file.getName()));
            //打印返回的信息
            logger.info(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.error(r.toString());
            try {
                //响应的文本信息
                logger.error(r.bodyString());
            } catch (QiniuException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
    }
}
