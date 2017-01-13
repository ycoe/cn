package com.duoec.cn.core.freemarker.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ycoe on 16/4/27.
 */
public class ComponentScanner extends ClassPathBeanDefinitionScanner {
    private static Logger logger = LoggerFactory.getLogger(ComponentScanner.class);
    private Map<String, Object> componentConfig;

    public ComponentScanner(BeanDefinitionRegistry registry, Map<String, Object> componentConfig) {
        super(registry, false);
        this.componentConfig = componentConfig;
        ComponentBeanNameGenerator componentBeanNameGenerator = new ComponentBeanNameGenerator();
        this.setBeanNameGenerator(componentBeanNameGenerator);
    }

    public void registerFilters() {
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set beanDefinitions = super.doScan(basePackages);
        if(!beanDefinitions.isEmpty()) {
            Iterator its = beanDefinitions.iterator();

            while(its.hasNext()) {
                BeanDefinitionHolder holder = (BeanDefinitionHolder)its.next();
                GenericBeanDefinition definition = (GenericBeanDefinition)holder.getBeanDefinition();
            }
        }

        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if(super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping ComponentScanner with name \'" + beanName + "\' and \'" + beanDefinition.getBeanClassName() + "\'" + ". Bean already defined with the same name!");
            return false;
        }
    }
}
