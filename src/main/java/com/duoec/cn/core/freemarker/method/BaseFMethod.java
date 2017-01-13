package com.duoec.cn.core.freemarker.method;

import com.duoec.cn.core.common.utils.SpringContextHolder;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ycoe on 16/4/28.
 */
public abstract class BaseFMethod implements TemplateMethodModelEx {
    private static final Logger logger = LoggerFactory.getLogger(BaseFMethod.class);

    @Override
    public Object exec(List arguments) throws TemplateModelException{
        Class<? extends BaseFMethod> methodClass = this.getClass();
        try {
            BaseFMethod method = methodClass.newInstance();
            SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(method);
            return method.invoke(arguments);
        } catch (Exception e) {
            logger.error("调用方法失败!", e);
            throw new TemplateModelException(e);
        }
    }

    public abstract Object invoke(List arguments) throws TemplateModelException;
}
