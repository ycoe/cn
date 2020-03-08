package com.duoec.web.core.freemarker.scanner;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * Created by ycoe on 16/4/27.
 */
public class ComponentScannerProcess implements BeanDefinitionRegistryPostProcessor {
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 需要扫描的包
     */
    private String basePackage;

    private Map<String, Object> componentConfig;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry){
        ComponentScanner scanner = new ComponentScanner(beanDefinitionRegistry, componentConfig);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ",; \t\n"));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
