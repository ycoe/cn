package com.duoec.web.cn.core.configure;

import com.duoec.web.core.freemarker.FreemarkerConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by xuwenzhen on 2020/3/1.
 */
@Component
public class CommonConfigure {
    @Value("${static.path}")
    private String staticPath;

    @PostConstruct
    public void init() {
        if (!staticPath.endsWith("/")) {
            staticPath += "/";
        }
        FreemarkerConst.STATIC_PATH = staticPath;
    }
}
