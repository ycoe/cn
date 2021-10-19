package com.duoec.api.w.service.impl;

import com.duoec.api.w.dto.request.BlogCommentQuery;
import com.duoec.api.w.dto.request.BlogQuery;
import com.duoec.api.w.dto.request.BlogSaveRequest;
import com.duoec.api.w.dto.response.BlogDetailVo;
import com.duoec.api.w.dto.response.BlogListVo;
import com.duoec.api.w.dto.response.BlogNewVo;
import com.duoec.api.w.dto.response.UserInfo;
import com.duoec.api.w.mongo.dao.BlogDao;
import com.duoec.api.w.mongo.entity.Blog;
import com.duoec.api.w.service.BlogService;
import com.duoec.api.w.service.UserService;
import com.duoec.web.base.core.interceptor.access.AuthInfo;
import com.duoec.web.base.exceptions.Http404Exception;
import com.fangdd.traffic.common.mongo.constant.MongoQueryOperatorsConsts;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author xuwenzhen
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogDao blogDao;

    @Autowired
    private UserService userService;

    /**
     * 查询微博（带分页）
     *
     * @param query 查询条件
     * @return 带分页的微博列表
     */
    @Override
    public BlogListVo<Blog> list(BlogQuery query) {
        BlogListVo<Blog> data = new BlogListVo<>();
        data.setTotal(0);
        Bson filter = getFilter(query);
        long total = blogDao.count(filter);
        if (total == 0) {
            return data;
        }
        data.setTotal((int) total);
        Integer pageSize = query.getPageSize();
        int skip = (query.getPageNo() - 1) * pageSize;
        List<Blog> list = blogDao.findEntities(filter, Sorts.descending(Blog.FIELD_CREATE_TIME), skip, pageSize);
        data.setList(list);

        Set<Integer> userIds = list.stream().map(Blog::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = userService.getUserInfos(userIds);
        data.setUserInfoList(userInfoList);
        return data;
    }

    /**
     * 保存微博信息
     *
     * @param request  需要保存的微博信息
     * @param authInfo 当前用户
     * @return 保存成功的微博信息
     */
    @Override
    public Blog save(BlogSaveRequest request, AuthInfo authInfo) {
        Long blogId = request.getId();
        long now = System.currentTimeMillis();
        List<Long> parentIds = request.getParentIds();
        if (blogId == null || blogId == 0) {
            //新增
            Blog blog = new Blog();
            blog.setId(request.getId());
            blog.setContent(request.getContent());
            blog.setTags(request.getTags());
            blog.setParentIds(parentIds);
            blog.setArticles(request.getArticles());
            blog.setSeedId(request.getSeedId());
            blog.setCateIds(request.getCateIds());
            blog.setImages(request.getImages());
            blog.setVideos(request.getVideos());
            blog.setFiles(request.getFiles());
            blog.setCreateTime(now);
            blog.setUpdateTime(now);
            blog.setUserId(authInfo.getId());
            blog.setDelete(false);
            blogDao.insertOne(blog);

            if (!CollectionUtils.isEmpty(parentIds)) {
                //如果是评论
                //更新用户信息
                userService.incUserBlogCommentCount(authInfo.getId(), 1);

                //源微博评论数+1
                blogDao.updateMany(Filters.in(Blog.FIELD_ID, parentIds), Updates.inc(Blog.FIELD_COMMENT_COUNT, 1));
            } else {
                userService.incUserBlogCount(authInfo.getId(), 1);
            }
            return blog;
        }
        //更新操作
        Blog blog = blogDao.getEntityById(blogId);
        if (blog == null) {
            throw new Http404Exception("找不到blogId=" + blogId + "的记录！");
        }
        blog.setContent(request.getContent());
        blog.setTags(request.getTags());
        blog.setParentIds(parentIds);
        blog.setArticles(request.getArticles());
        blog.setSeedId(request.getSeedId());
        blog.setCateIds(request.getCateIds());
        blog.setImages(request.getImages());
        blog.setVideos(request.getVideos());
        blog.setFiles(request.getFiles());
        blog.setUpdateTime(now);
        blogDao.updateEntity(blog);
        return blog;
    }

    /**
     * 删除某条微博
     *
     * @param blogId   微博ID
     * @param authInfo 当前用户
     */
    @Override
    public int delete(Long blogId, AuthInfo authInfo) {
        Bson filter = Filters.eq(Blog.FIELD_ID, blogId);
        Blog existsBlog = blogDao.getEntity(filter, Projections.include(
                Blog.FIELD_PARENT_IDS,
                Blog.FIELD_SEED_ID,
                Blog.FIELD_COMMENT_COUNT
        ));
        if (existsBlog == null) {
            throw new Http404Exception("找不到blogId=" + blogId + "的记录！");
        }

        //逻辑删除
        blogDao.updateMany(Filters.or(
                filter,
                Filters.eq(Blog.FIELD_SEED_ID, blogId)
        ), Updates.set(Blog.FIELD_DELETE, true));

        Integer commentCount = 0;
        //扣减用户计数
        if (CollectionUtils.isEmpty(existsBlog.getParentIds())) {
            userService.incUserBlogCount(authInfo.getId(), -1);
        } else {
            //如果是评论
            userService.incUserBlogCommentCount(authInfo.getId(), -1);

            commentCount = existsBlog.getCommentCount();
            if (commentCount == null) {
                commentCount = 0;
            }
            blogDao.updateOne(Filters.and(
                    Filters.in(Blog.FIELD_ID, existsBlog.getParentIds()),
                    Filters.gte(Blog.FIELD_COMMENT_COUNT, commentCount)
            ), Updates.inc(Blog.FIELD_COMMENT_COUNT, -commentCount - 1));
        }
        return commentCount + 1;
    }

    /**
     * 获取某个微博的详情
     *
     * @param blogId 微博ID
     * @return 微博详情
     */
    @Override
    public BlogDetailVo detail(long blogId) {
        Bson filter = Filters.eq(Blog.FIELD_ID, blogId);
        Blog blog = blogDao.getEntity(filter);

        if (blog == null) {
            throw new Http404Exception("找不到blogId=" + blogId + "的记录！");
        }
        Set<Integer> userIds = Sets.newHashSet();
        userIds.add(blog.getUserId());
        Map<Long, Blog> blogMap = Maps.newHashMap();
        blogMap.put(blogId, blog);

        blogDao
                .find(Filters.and(
                        Filters.eq(Blog.FIELD_SEED_ID, blogId),
                        Filters.eq(Blog.FIELD_DELETE, false)
                ))
                .sort(Sorts.ascending(Blog.FIELD_ID))
                .forEach((Consumer<? super Blog>) comment -> {
                    Blog parent = blogMap.get(comment.getParentIds().get(comment.getParentIds().size() - 1));
                    if (parent == null) {
                        return;
                    }
                    userIds.add(comment.getUserId());
                    blogMap.put(comment.getId(), comment);
                    List<Blog> comments = parent.getComments();
                    if (comments == null) {
                        comments = Lists.newArrayList();
                        parent.setComments(comments);
                    }
                    comments.add(comment);
                });

        BlogDetailVo data = new BlogDetailVo();
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserInfo> userInfos = userService.getUserInfos(userIds);
            data.setUserInfoList(userInfos);
        }

        data.setBlog(blog);
        return data;
    }

    /**
     * 获取用于编辑的信息
     *
     * @param blogId 微博ID
     * @return 微博编辑信息
     */
    @Override
    public Blog get(long blogId) {
        Bson filter = Filters.eq(Blog.FIELD_ID, blogId);
        Blog blog = blogDao.getEntity(filter);

        if (blog == null) {
            throw new Http404Exception("找不到blogId=" + blogId + "的记录！");
        }

        return blog;
    }

    /**
     * 获取某个博客的评论列表
     *
     * @param query 评论列表请求
     * @return 评论列表
     */
    @Override
    public BlogListVo<Blog> getComments(BlogCommentQuery query) {
        BlogListVo<Blog> data = new BlogListVo<>();
        data.setTotal(0);
        Document filter = new Document(Blog.FIELD_DELETE, false);
        filter.put(Blog.FIELD_SEED_ID, query.getBlogId());
        long total = blogDao.count(filter);
        if (total == 0) {
            return data;
        }
        data.setTotal((int) total);
        Integer pageSize = query.getPageSize();
        int skip = (query.getPageNo() - 1) * pageSize;
        List<Blog> list = blogDao.findEntities(filter, Sorts.descending(Blog.FIELD_CREATE_TIME), skip, pageSize);
        data.setList(list);

        Set<Integer> userIds = list.stream().map(Blog::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = userService.getUserInfos(userIds);
        data.setUserInfoList(userInfoList);

        return data;
    }

    /**
     * 查询{time}之后，新的微博 + 评论数量
     *
     * @param query 筛选条件
     * @return 新的数量
     */
    @Override
    public int count(BlogQuery query) {
        Bson filter = getFilter(query);
        long count = blogDao.count(filter);
        return Math.toIntExact(count);
    }

    /**
     * 返回{time}之后新的微博 + 评论
     *
     * @param query 查询条件
     * @return 列表
     */
    @Override
    public BlogListVo<BlogNewVo> listNewBlog(BlogQuery query) {
        BlogListVo<BlogNewVo> vo = new BlogListVo<>();
        Bson filter = getFilter(query);
        long total = blogDao.count(filter);
        vo.setTotal((int) total);

        if (total > 0) {
            List<BlogNewVo> blogList = blogDao.getDocumentMongoCollection(BlogNewVo.class)
                    .find(filter)
                    .sort(Sorts.descending(Blog.FIELD_UPDATE_TIME))
                    .into(Lists.newArrayList());

            //如果是回复，则设置原创作内容
            setCommendParents(blogList);

            Set<Integer> userIds = blogList.stream().map(BlogNewVo::getUserId).collect(Collectors.toSet());
            List<UserInfo> userInfoList = userService.getUserInfos(userIds);
            vo.setUserInfoList(userInfoList);

            vo.setList(blogList);
        }

        return vo;
    }

    private void setCommendParents(List<BlogNewVo> blogs) {
        if (CollectionUtils.isEmpty(blogs)) {
            return;
        }
        Set<Long> blogIds = Sets.newHashSet();
        Map<Long, List<BlogNewVo>> blogMap = Maps.newHashMap();
        blogs.stream().filter(blog -> !CollectionUtils.isEmpty(blog.getParentIds())).forEach(blog -> {
            long seedId = blog.getParentIds().get(0);
            blogMap.computeIfAbsent(seedId, k -> Lists.newArrayList()).add(blog);
            blogIds.add(seedId);
        });
        if (CollectionUtils.isEmpty(blogIds)) {
            return;
        }
        blogDao.find(Filters.in(Blog.FIELD_ID, blogIds)).forEach((Consumer<? super Blog>) blog -> {
            Long seedId = blog.getId();
            List<BlogNewVo> recommends = blogMap.get(seedId);
            if (CollectionUtils.isEmpty(recommends)) {
                return;
            }
            recommends.forEach(recommend -> recommend.setParent(blog));
        });
    }

    private Bson getFilter(BlogQuery query) {
        Document filter = new Document(Blog.FIELD_DELETE, false);
        if (query.getSeed() == null || query.getSeed()) {
            //不加载评论
            filter.put(Blog.FIELD_SEED_ID, new Document(MongoQueryOperatorsConsts.EXISTS, false));
        }
        Long startTime = query.getStartTime();
        if (startTime != null && startTime > 0) {
            filter.put(Blog.FIELD_UPDATE_TIME, new Document(MongoQueryOperatorsConsts.GT, startTime));
        }
        Long endTime = query.getEndTime();
        if (endTime != null && endTime > 0) {
            Document updateTimeOpts = (Document) filter.get(Blog.FIELD_UPDATE_TIME);
            if (updateTimeOpts == null) {
                filter.put(Blog.FIELD_UPDATE_TIME, new Document(MongoQueryOperatorsConsts.LT, endTime));
            } else {
                updateTimeOpts.put(MongoQueryOperatorsConsts.LT, endTime);
            }
        }

        return filter;
    }
}
