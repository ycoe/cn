package com.duoec.web.core.freemarker.scanner;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.beans.Introspector;

/**
 * Created by ycoe on 16/4/27.
 */
public class ComponentBeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String beanName = definition.getBeanClassName();
        return Introspector.decapitalize(beanName);
    }
}
