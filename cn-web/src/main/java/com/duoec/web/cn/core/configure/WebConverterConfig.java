package com.duoec.web.cn.core.configure;

import com.duoec.web.base.core.interceptor.access.AccessInterceptor;
import com.duoec.web.cn.core.interceptor.ViewCacheInterceptor;
import com.duoec.web.base.service.SiteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 2019/4/14
 */
@Configuration
@ConditionalOnBean({SiteService.class})
public class WebConverterConfig implements WebMvcConfigurer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //注册jackson序列化方法
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        converters.add(converter);

        //注册二进制响应
        converters.add(byteArrayHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AccessInterceptor accessInterceptor = new AccessInterceptor();
        autowireCapableBeanFactory.autowireBean(accessInterceptor);
        registry.addInterceptor(accessInterceptor);

        ViewCacheInterceptor interceptor = new ViewCacheInterceptor();
        autowireCapableBeanFactory.autowireBean(interceptor);
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 参考文档：https://www.baeldung.com/spring-mvc-image-media-data
     *
     * @return
     */
    private ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_OCTET_STREAM));
        return arrayHttpMessageConverter;
    }
}