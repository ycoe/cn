package com.duoec.cn.core.freemarker.scanner;

import com.duoec.cn.core.common.utils.SpringContextHolder;
import com.duoec.cn.core.freemarker.PortletRegistException;
import com.duoec.cn.core.freemarker.method.FMethod;
import com.duoec.cn.core.freemarker.pipe.Pipe;
import com.duoec.cn.core.freemarker.portlet.BasePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletProxy;
import com.google.common.base.Strings;
import freemarker.core.BasePipe;
import freemarker.template.TemplateMethodModelEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/4/27.
 */
public class FreeMarkerComponentRegist {
    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerComponentRegist.class);

    private List<String> packages;

    FreeMarkerConfigurer freeMarkerConfigurer;

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    private Map<String, Object> freeMarkerVariables = new HashMap<>();

    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    public void scan() throws Exception {
        if(packages == null || packages.isEmpty()){
            return;
        }
        for (String packagePattern : packages){
            scanFreemarkerExtends(packagePattern);
        }
        freeMarkerConfigurer.setFreemarkerVariables(freeMarkerVariables);
    }

    private void scanFreemarkerExtends(String packagePattern) throws Exception {
        autowireCapableBeanFactory = SpringContextHolder.getApplicationContext().getAutowireCapableBeanFactory();
        String ex = "classpath*:" + ClassUtils.convertClassNameToResourcePath(packagePattern);
        Resource[] resources = this.resourcePatternResolver.getResources(ex);
        if(resources.length == 0)
            return;
        for (Resource resource : resources){
            if(!resource.isReadable() || !resource.getFilename().endsWith(".class")) {
                continue;
            }
            MetadataReader metadata = metadataReaderFactory.getMetadataReader(resource);
            String className = metadata.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(className);

            if(BasePortlet.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Portlet.class)){
                registFreemarkerPortlet((Class<? extends BasePortlet>) clazz);
                continue;
            }

            if(TemplateMethodModelEx.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(FMethod.class)){
                registFreemarkerMethod(clazz);
                continue;
            }

            if(BasePipe.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Pipe.class)){
                registPipe(clazz);
            }
        }
    }

    private void registPipe(Class<?> clazz) {
        Pipe pipe = clazz.getAnnotation(Pipe.class);
        String name = pipe.value();
        try {
            BasePipe pipeObj = (BasePipe) clazz.newInstance();
            autowireCapableBeanFactory.autowireBean(pipeObj);
            BasePipe.regist(name, pipeObj);
            logger.info("注册pipe:{} => {}", name, clazz.getName());
        } catch (InstantiationException e) {
            throw new PortletRegistException(e);
        } catch (IllegalAccessException e) {
            throw new PortletRegistException(e);
        }
    }

    private void registFreemarkerMethod(Class<?> clazz) {
        String className = clazz.getName();
        FMethod method = clazz.getAnnotation(FMethod.class);
        String name = method.value();
        if(Strings.isNullOrEmpty(name)){
            return;
        }
        if(name.contains("-") || name.contains(" ")){
            logger.error("类{} @Component名称不能包含空格或中横线!", className);
            return;
        }
        if (freeMarkerVariables.containsKey(name)){
            logger.error("method名称\"{}\"重复:{}, {}", name, freeMarkerVariables.get(name).getClass().getName(), className);
            throw new PortletRegistException("method名称重复!");
        }
        TemplateMethodModelEx methodObj = null;
        try {
            methodObj = (TemplateMethodModelEx) clazz.newInstance();
//            autowireCapableBeanFactory.autowireBean(methodObj); //在调用处再进行注入
        } catch (InstantiationException e) {
            throw new PortletRegistException(e);
        } catch (IllegalAccessException e) {
            throw new PortletRegistException(e);
        }
        logger.info("注册method:{} => {}", name, className);
        freeMarkerVariables.put(name, methodObj);
    }

    private void registFreemarkerPortlet(Class<? extends BasePortlet> clazz){
        String className = clazz.getName();
        Portlet portlet = clazz.getAnnotation(Portlet.class);
        String name = portlet.value();

//        BasePortlet portletObj;
//        try {
//            portletObj = clazz.newInstance();
//            autowireCapableBeanFactory.autowireBean(portletObj);
//        } catch (InstantiationException e) {
//            throw new PortletException(e);
//        } catch (IllegalAccessException e) {
//            throw new PortletException(e);
//        }
//        portletObj.setDir(portlet.dir());
        if(Strings.isNullOrEmpty(name)){
            return;
        }
        if(name.contains("-") || name.contains(" ")){
            logger.error("类{} @Portlet名称不能包含空格或中横线!", className);
            return;
        }
        if (freeMarkerVariables.containsKey(name)){
            logger.error("portlet名称\"{}\"重复:{}, {}", name, freeMarkerVariables.get(name).getClass().getName(), className);
            throw new PortletRegistException("portlet名称重复!");
        }
        logger.info("注册portlet:{} => {}", name, className);
        PortletProxy portletProxy = new PortletProxy(clazz, portlet.dir());
        freeMarkerVariables.put(name, portletProxy);
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }
}
