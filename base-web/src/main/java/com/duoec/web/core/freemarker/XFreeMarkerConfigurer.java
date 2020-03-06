package com.duoec.web.core.freemarker;

import com.duoec.web.core.freemarker.scanner.FreeMarkerComponentRegist;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;

/**
 * Created by ycoe on 16/4/27.
 */
public class XFreeMarkerConfigurer extends FreeMarkerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(XFreeMarkerConfigurer.class);

    private static XFreeMarkerConfigurer xFreeMarkerConfigurer;

    private List<String> packages;

    public XFreeMarkerConfigurer() {
        xFreeMarkerConfigurer = this;
    }

    public static XFreeMarkerConfigurer get() {
        return xFreeMarkerConfigurer;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        FreeMarkerComponentRegist regist = new FreeMarkerComponentRegist();
        regist.setPackages(packages);
        regist.setFreeMarkerConfigurer(this);
        try {
            regist.scan();
        } catch (Exception e) {
            logger.error("扫描宏/方法失败!", e);
        }
        //注意,此行必须放在后面,否则会报错
        super.afterPropertiesSet();
    }
}
