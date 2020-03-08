package com.duoec.web.core.freemarker.portlet;

import com.duoec.web.base.core.SpringContextHolder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 *
 * Created by ycoe on 16/5/16.
 */
public class PortletProxy  implements TemplateDirectiveModel {
    private static final Logger logger = LoggerFactory.getLogger(PortletProxy.class);

    private Class<? extends BasePortlet> clazz;

    private String dir;

    public PortletProxy(Class<? extends BasePortlet> clazz, String dir) {
        this.clazz = clazz;
        this.dir = dir;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        BasePortlet newPortlet = null;
        try {
            newPortlet = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("初始化" + clazz.getName() + "实例失败!", e);
        } catch (IllegalAccessException e) {
            logger.error("初始化" + clazz.getName() + "实例失败!", e);
        }

        if(newPortlet != null) {
            SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(newPortlet);
            newPortlet.setDir(dir);
            newPortlet.execute(env, params, loopVars, body);
        }
    }
}
