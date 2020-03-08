package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.base.dto.response.BaseResponse;
import com.duoec.web.base.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycoe
 * @date 17/1/17
 */
@RestController
@RequestMapping("/manager/uploader")
public class UploaderBackendController extends BackendController {
    @Value("${qiniu.bucket.cn:duoec}")
    private String cnBucket;

    @Autowired
    private QiniuService qiniuService;

    @GetMapping("/token")
//    @Access(value = RoleEnum.Admin)
    public BaseResponse<String> getUploadToken() {
        return BaseResponse.success(qiniuService.getUploadToken(cnBucket));
    }
}
