package com.duoec.api.w.service;

import com.duoec.api.w.dto.request.BlogQuery;
import com.duoec.api.w.dto.request.BlogSaveRequest;
import com.duoec.api.w.dto.response.BlogDetailVo;
import com.duoec.api.w.dto.response.BlogListVo;
import com.duoec.api.w.mongo.entity.Blog;
import com.duoec.web.base.core.interceptor.access.AuthInfo;

/**
 * 微博内容服务
 *
 * @author xuwenzhen
 */
public interface BlogService {
    /**
     * 查询微博（带分页）
     *
     * @param query 查询条件
     * @return 带分页的微博列表
     */
    BlogListVo list(BlogQuery query);

    /**
     * 保存微博信息
     *
     * @param request  需要保存的微博信息
     * @param authInfo 当前用户
     * @return 保存成功的微博信息
     */
    Blog save(BlogSaveRequest request, AuthInfo authInfo);

    /**
     * 删除某条微博
     *
     * @param blogId   微博ID
     * @param authInfo 当前用户
     */
    void delete(Long blogId, AuthInfo authInfo);

    /**
     * 获取某个微博的详情
     *
     * @param blogId 微博ID
     * @return 微博详情
     */
    BlogDetailVo detail(long blogId);

    /**
     * 获取用于编辑的信息
     *
     * @param blogId 微博ID
     * @return 微博编辑信息
     */
    Blog get(long blogId);
}
