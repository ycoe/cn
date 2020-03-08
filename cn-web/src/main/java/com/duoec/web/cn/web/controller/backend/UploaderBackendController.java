package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.base.service.QiniuService;
import com.duoec.web.base.core.interceptor.access.Access;
import com.duoec.web.base.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ycoe
 * @date 17/1/17
 */
@Controller
@RequestMapping("/manager/uploader")
public class UploaderBackendController extends BackendController {
    @Value("${qiniu.bucket.cn:duoec}")
    private String cnBucket;

    @Autowired
    private QiniuService qiniuService;

    @GetMapping("/token")
    @Access(value = RoleEnum.Admin, contentType = ContentTypeEnum.APPLICATION_JSON)
    public ModelAndView getUploadToken() {
        return success(qiniuService.getUploadToken(cnBucket));
    }
}
