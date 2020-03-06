package com.duoec.web.core.freemarker.portlet.common;

import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.web.core.freemarker.portlet.PortletParam;
import com.duoec.web.core.freemarker.service.ICache;
import com.duoec.web.core.utils.MD5Utils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;

/**
 *
 * @author ycoe
 * @date 16/5/20
 */
public abstract class ResourcePortlet extends BaseFuturePortlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcePortlet.class);

    private static final int DAY = 3600 * 24;

    @Qualifier("redisCache")
    @Autowired
    ICache cache;

    /**
     * 引入模式： 0压缩进同一文件 1只作排重处理，还是使用原原始方式引入
     */
    @PortletParam
    protected int type = 0;

    @PortletParam
    protected String src; //引入的js脚本

    protected void saveToCache(String key, String content) {
        cache.set("assets:" + key, content, DAY * 10);
        LOGGER.info("set assets:{}", key);
    }

    protected File getSourceFile() throws PortletException {
        if(Strings.isNullOrEmpty(src)){
            return null;
        }
        String srcPath = src;
        if(src.startsWith("http://") || src.startsWith("https://")) {
            //网络资源
            throw new PortletException(env, "暂时不支持网络脚本!");
        }
        if(srcPath.startsWith("/")) {
            srcPath = srcPath.substring(1);
        }
        srcPath = srcPath.replace("assets", "");
        srcPath = FreemarkerConst.STATIC_PATH + srcPath;

        File resourceFile = new File(srcPath);
        if(!resourceFile.exists()) {
            LOGGER.error("文件不存在:{}", srcPath);
            return null;
        }
        return resourceFile;
    }

    protected String getResourceName(long lastModifyTime){
        return MD5Utils.md5(resourceVars.getTemplateName() + lastModifyTime);
    }
}
