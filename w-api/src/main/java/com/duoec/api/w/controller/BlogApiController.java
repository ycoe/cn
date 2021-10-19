package com.duoec.api.w.controller;

import com.duoec.api.w.dto.request.BlogCommentQuery;
import com.duoec.api.w.dto.request.BlogQuery;
import com.duoec.api.w.dto.request.BlogSaveRequest;
import com.duoec.api.w.dto.response.BlogDetailVo;
import com.duoec.api.w.dto.response.BlogEditVo;
import com.duoec.api.w.dto.response.BlogListVo;
import com.duoec.api.w.dto.response.BlogNewVo;
import com.duoec.api.w.mongo.entity.Blog;
import com.duoec.api.w.service.BlogService;
import com.duoec.web.base.core.interceptor.access.Access;
import com.duoec.web.base.core.interceptor.access.AuthInfo;
import com.duoec.web.base.core.interceptor.access.AuthInfoHolder;
import com.duoec.web.base.dto.response.BaseResponse;
import com.duoec.web.base.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuwenzhen
 * @chapter 内容服务
 * @section 博客服务
 */
@RestController
@RequestMapping("/api/blog")
public class BlogApiController {
    @Value("${qiniu.bucket.w:duo-w}")
    private String wBucket;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private BlogService blogService;

    /**
     * 查询带分页的微博列表
     *
     * @param query 查询条件
     * @return 带分页的微博列表
     */
    @Access
    @GetMapping("/list")
    public BaseResponse<BlogListVo<Blog>> list(BlogQuery query) {
        if (query.getPageNo() == null) {
            query.setPageNo(1);
        }
        if (query.getPageSize() == null) {
            query.setPageSize(10);
        }
        return BaseResponse.success(blogService.list(query));
    }

    /**
     * 获取某个微博详情
     *
     * @param blogId blog.id
     * @return 获取微博详细信息
     */
    @Access
    @GetMapping("/{blogId:\\d+}")
    public BaseResponse<BlogDetailVo> get(@PathVariable long blogId) {
        return BaseResponse.success(blogService.detail(blogId));
    }

    /**
     * 获取某个微博信息（用于编辑）
     *
     * @param blogId blog.id
     * @return 获取微博信息
     */
    @Access
    @GetMapping("/edit/{blogId:\\d+}")
    public BaseResponse<BlogEditVo> getById(@PathVariable long blogId) {
        BlogEditVo vo = new BlogEditVo();
        if (blogId > 0) {
            Blog blog = blogService.get(blogId);
            vo.setBlog(blog);
        }
        vo.setToken(qiniuService.getUploadToken(wBucket));
        return BaseResponse.success(vo);
    }

    /**
     * 获取某个微博的评论列表
     *
     * @param blogId blog.id
     * @return 评论列表
     */
    @Access
    @GetMapping("/{blogId:\\d+}/comment")
    public BaseResponse<BlogListVo> getComments(@PathVariable long blogId, BlogCommentQuery query) {
        query.setBlogId(blogId);
        return BaseResponse.success(blogService.getComments(query));
    }

    /**
     * 保存微博
     *
     * @param request 需要保存的微博信息
     * @return 保存成功的微博信息
     */
    @Access
    @PostMapping
    public BaseResponse<Blog> save(@RequestBody BlogSaveRequest request) {
        AuthInfo authInfo = AuthInfoHolder.get();
        return BaseResponse.success(blogService.save(request, authInfo));
    }

    /**
     * 删除某条微博
     *
     * @param blogId 微博ID
     * @return 删除结果
     */
    @Access
    @DeleteMapping("/{blogId:\\d+}")
    public BaseResponse<Integer> delete(@PathVariable Long blogId) {
        AuthInfo authInfo = AuthInfoHolder.get();
        return BaseResponse.success(blogService.delete(blogId, authInfo));
    }

    /**
     * 查询某些条件下发布的微博+评论数量
     *
     * @param query 查询条件
     * @return 数量
     */
//    @Access
    @GetMapping("/count")
    public BaseResponse<Integer> getBlogCount(BlogQuery query) {
        return BaseResponse.success(blogService.count(query));
    }

    /**
     * 拉取上次之后的新微博+评论数
     *
     * @param query 查询条件
     * @return 数量
     */
//    @Access
    @GetMapping("/new")
    public BaseResponse<BlogListVo<BlogNewVo>> getNewBlogList(BlogQuery query) {
        query.setSeed(false);
        return BaseResponse.success(blogService.listNewBlog(query));
    }
}
