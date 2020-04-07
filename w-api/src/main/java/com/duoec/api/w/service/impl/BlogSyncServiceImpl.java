package com.duoec.api.w.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duoec.api.w.entity.AttArticleEntity;
import com.duoec.api.w.entity.PostsEntity;
import com.duoec.api.w.mapper.*;
import com.duoec.api.w.mongo.dao.ArticleDao;
import com.duoec.api.w.mongo.dao.BlogDao;
import com.duoec.api.w.mongo.dao.UserDao;
import com.duoec.api.w.mongo.entity.*;
import com.duoec.api.w.service.BlogSyncService;
import com.duoec.web.base.service.QiniuService;
import com.duoec.web.base.utils.MD5Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author xuwenzhen
 */
@Service
public class BlogSyncServiceImpl implements BlogSyncService {
    private static final Logger logger = LoggerFactory.getLogger(BlogSyncServiceImpl.class);
    private static final String DOWNLOAD_PATH = "/Users/xuwenzhen/Downloads/w/";

    @Value("${duo-w.password.salt:}")
    private String salt;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    @Qualifier("blogArticleDao")
    private ArticleDao articleDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private PostAttachmentMapper postAttachmentMapper;

    @Autowired
    private AttFilesMapper attFilesMapper;

    @Autowired
    private AttPhotoMapper attPhotoMapper;

    @Autowired
    private AttArticleMapper attArticleMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Map<String, String> RESOURCE_MAP = Maps.newHashMap();

    @PostConstruct
    public void init() {
        List<String> lines = null;
        try {
            lines = Resources.readLines(Resources.getResource("resourceMap.txt"), Charsets.UTF_8);
        } catch (IOException e) {
            logger.error("读取资源文件失败：resourceMap.txt", e);
            return;
        }
        if (!CollectionUtils.isEmpty(lines)) {
            lines.forEach(line -> {
                String[] array = line.split(":");
                if (array.length == 2) {
                    String key = array[0];
                    String value = array[1];
                    RESOURCE_MAP.put(key, value);
                }
            });
        }
    }

    /**
     * 同步
     *
     * @return 是否同步成功
     */
    @Override
    public Boolean sync() {
        QueryWrapper<PostsEntity> query = new QueryWrapper<>();
        query.lambda().orderByAsc(PostsEntity::getId);
        Map<Long, Blog> blogMap = Maps.newHashMap();

        List<Blog> blogList = Lists.newArrayList();
        List<Article> articleList = Lists.newArrayList();
        List<User> userList = Lists.newArrayList();

        Map<Integer, String> fileMap = Maps.newHashMap();
        Map<Integer, BlogImage> photoMap = Maps.newHashMap();
        Map<Integer, AttArticleEntity> articleMap = Maps.newHashMap();
        Map<Integer, User> userMap = Maps.newHashMap();

        //userId, blogId, articleId
        Long[] maxIds = new Long[] {0L, 0L, 0L};

        usersMapper.selectList(new QueryWrapper<>()).forEach(item -> {
            User user = new User();
            user.setId(item.getId());
            user.setName(item.getUsername());
            String avatar = item.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                String url = "http://w.duoec.com/upload/avatars/300X300/" + avatar;
                String qiniuUrl = syncImage(url);
                user.setAvatar("http://w-assets.duoec.com/" + qiniuUrl);
            }
            user.setLoginCount(item.getLoginCount());
            user.setBlogCount(0);
            user.setBlogCommentCount(0);
            user.setArticleCount(0);
            String pwd = MD5Utils.md5(salt + item.getPassword());
            user.setPassword(pwd);
            user.setCreateTime(item.getRegistTime() * 1000L);
            user.setLastLoginTime(item.getLastLoginTime() * 1000L);
            userMap.put(item.getId(), user);
            userList.add(user);
            maxIds[0] = Math.max(maxIds[0], item.getId());
        });

        attPhotoMapper.selectList(new QueryWrapper<>()).forEach(item -> {
            long time = item.getCreateTime() * 1000L;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            String url = "http://w.duoec.com/upload/" + year + ((month > 9 ? "" : "0") + month) + "/500X500/" + item.getFilename();
            String qiniuUrl = syncImage(url);

            BlogImage image = new BlogImage();
            image.setUrl("http://w-assets.duoec.com/" + qiniuUrl);
            image.setTitle(item.getFilename());

            photoMap.put(item.getId(), image);
            System.out.println(url);
        });

        attArticleMapper.selectList(new QueryWrapper<>()).forEach(item -> {
            articleMap.put(item.getId(), item);
            Article article = new Article();
            article.setId(item.getId().longValue());
            article.setTitle(item.getTitle());
            article.setContent(item.getBody());
//            article.setImages(item.getImages());
//            article.setCategoryIds(copy.getCategoryIds());
            article.setCreateTime(item.getCreateTime() * 1000L);
            article.setUpdateTime(article.getCreateTime());
            articleList.add(article);

            User user = userMap.get(item.getUserId());
            user.setArticleCount(user.getArticleCount() + 1);
        });

        attFilesMapper.selectList(new QueryWrapper<>()).forEach(item -> {
            fileMap.put(item.getId(), item.getFileName());
        });

        postsMapper.selectList(query).forEach(item -> {
            User user = userMap.get(item.getUserId());

            maxIds[1] = Math.max(maxIds[1], item.getId());
            Blog blog = convert(item);
            blogList.add(blog);
            blogMap.put(blog.getId(), blog);
            if (item.getSeedId() > 0) {
                //尝试找到父级
                Integer seedId = item.getParentId();
                Blog parentBlog = blogMap.get(seedId.longValue());
                if (parentBlog == null) {
                    logger.warn("无法找到seedId={}的节点", seedId);
                    return;
                }

                Integer commentCount = parentBlog.getCommentCount();
                if (commentCount == null) {
                    commentCount = 0;
                }
                parentBlog.setCommentCount(commentCount + 1);
                user.setBlogCommentCount(user.getBlogCommentCount() + 1);
            } else {
                user.setBlogCount(user.getBlogCount() + 1);
            }
        });

        postAttachmentMapper.selectList(new QueryWrapper<>()).forEach(item -> {
            long blogId = item.getPostId().longValue();
            Blog blog = blogMap.get(blogId);
            if (blog == null) {
                logger.warn("无法找到seedId={}的节点", blogId);
                return;
            }
            String type = item.getType();
            if ("file".equals(type)) {
                //文件
                String fileName = fileMap.get(item.getAttachmentId());
                if (fileName == null) {
                    logger.warn("无法找到fileId={}数据", item.getAttachmentId());
                    return;
                }
                List<BlogFile> files = blog.getFiles();
                if (files == null) {
                    files = Lists.newArrayList();
                    blog.setFiles(files);
                }
                BlogFile file = new BlogFile();
                file.setFileName(fileName);
                file.setUrl("");
                file.setSize(0L);
                files.add(file);
            } else if ("article".equals(type)) {
                AttArticleEntity entity = articleMap.get(item.getAttachmentId());
                if (entity == null) {
                    logger.warn("无法找到articleId={}数据", item.getAttachmentId());
                    return;
                }
                List<BlogArticle> articles = blog.getArticles();
                if (articles == null) {
                    articles = Lists.newArrayList();
                    blog.setArticles(articles);
                }
                BlogArticle article = new BlogArticle();
                article.setArticleId(entity.getId().longValue());
                article.setTitle(entity.getTitle());
                articles.add(article);
            } else if ("photo".equals(type)) {
                BlogImage image = photoMap.get(item.getAttachmentId());
                if (image == null) {
                    logger.warn("无法找到photoId={}数据", item.getAttachmentId());
                    return;
                }
                List<BlogImage> images = blog.getImages();
                if (images == null) {
                    images = Lists.newArrayList();
                    blog.setImages(images);
                }
                images.add(image);
            }
        });

        blogDao.insertMany(blogList);
        articleDao.insertMany(articleList);
        userDao.insertMany(userList);

        blogDao.getNextSequence("user", maxIds[0], 1);
        blogDao.getNextSequence("blog", maxIds[1], 1);
        blogDao.getNextSequence("article", maxIds[2], 1);
        return true;
    }

    /**
     * 同步图片到七牛空间
     *
     * @param imageUrl 需要同步的图片URL
     * @return
     */
    public String syncImage(String imageUrl) {
        UploadManager uploadManager = getUploadManager();
        String upToken = qiniuService.getUploadToken("duo-w");

        String fileName = download(imageUrl);

        String key = RESOURCE_MAP.get(fileName);
        if (!Strings.isNullOrEmpty(key)) {
            return key;
        }
        try {
            key = "photo/" + fileName;
            Response response = uploadManager.put(DOWNLOAD_PATH + fileName, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
            File resourceMap = new File("/Users/xuwenzhen/Projects/duoec/cn/w-api/src/main/resources/resourceMap.txt");
            FileUtils.writeStringToFile(resourceMap, "\n" + fileName + ":" + key, Charsets.UTF_8, true);
            RESOURCE_MAP.put(fileName, key);
            System.out.println("上传成功：" + key);
        } catch (Exception e) {
            logger.error("上传失败:{}", fileName, e);
        }

        return key;
    }

    private UploadManager getUploadManager() {
        Configuration cfg = new Configuration(Region.huanan());
        return new UploadManager(cfg);
    }

    private String download(String url) {
        int index = url.lastIndexOf("/");
        String substring = url.substring(index + 1);
        System.out.println(substring);
        File dir = new File(DOWNLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File destination = new File(DOWNLOAD_PATH + substring);
        if (destination.exists()) {
            //文件已经存在
            return substring;
        }
        try {
            FileUtils.copyURLToFile(new URL(url), destination, 10000, 10000);
            return substring;
        } catch (IOException e) {
            logger.error("下载文件失败:{}", url, e);
            destination.deleteOnExit();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://w.duoec.com/upload/201009/500X500/1284826294130914.png";

    }

    private Blog convert(PostsEntity entity) {
        Blog blog = new Blog();
        blog.setDelete(false);
        blog.setCommentCount(0);
        blog.setId(entity.getId().longValue());
        blog.setContent(entity.getBody());
        blog.setUserId(entity.getUserId());
        if (entity.getParentId() > 0) {
            blog.setParentIds(Lists.newArrayList(entity.getParentId().longValue()));
        }
        if (entity.getSeedId() > 0) {
            blog.setSeedId(entity.getSeedId().longValue());
        }

        blog.setCreateTime(entity.getCreateTime() * 1000L);
        blog.setUpdateTime(blog.getCreateTime());
        return blog;
    }
}
