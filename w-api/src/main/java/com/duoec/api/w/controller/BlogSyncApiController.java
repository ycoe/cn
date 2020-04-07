package com.duoec.api.w.controller;

import com.duoec.api.w.service.BlogSyncService;
import com.duoec.web.base.dto.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuwenzhen
 * @chapter 内容服务
 * @section 信息服务
 */
@RestController
@RequestMapping("/api/blog")
public class BlogSyncApiController {
    @Autowired
    private BlogSyncService blogSyncService;

    /**
     * 同步数据
     */
    @GetMapping("/sync")
    public BaseResponse<Boolean> list() {
        return BaseResponse.success(blogSyncService.sync());
    }
}
