package com.duoec.web.core.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by ycoe on 16/4/28.
 */
public abstract class BaseFMethod implements TemplateMethodModelEx {
    private static final Logger logger = LoggerFactory.getLogger(BaseFMethod.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object exec(List arguments) throws TemplateModelException{
        Class<? extends BaseFMethod> methodClass = this.getClass();
        try {
            BaseFMethod method = methodClass.newInstance();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(method);
            return method.invoke(arguments);
        } catch (Exception e) {
            logger.error("调用方法失败!", e);
            throw new TemplateModelException(e);
        }
    }

    public abstract Object invoke(List arguments) throws TemplateModelException;
}
