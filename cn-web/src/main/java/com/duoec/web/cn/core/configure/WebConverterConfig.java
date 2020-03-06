package com.duoec.web.cn.core.configure;

import com.duoec.web.cn.core.interceptor.ViewCacheInterceptor;
import com.duoec.web.cn.core.interceptor.access.AccessInterceptor;
import com.duoec.web.core.freemarker.service.SiteService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author xuwenzhen
 * @date 2019/4/14
 */
@Configuration
@ConditionalOnBean({SiteService.class})
public class WebConverterConfig implements WebMvcConfigurer {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //注册jackson序列化方法
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = getObjectMapper();
//        converter.setObjectMapper(mapper);
//        converters.add(converter);

        //注册二进制响应
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                //反序列化时，忽略目标对象没有的属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

                //默认Date的格式化返回
                .setDateFormat(new SimpleDateFormat(DATE_FORMAT_PATTERN))

                //下面配置是值为null时，不显示
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)

                //下面一个配置是集合返回为空时，不显示
                .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false)
                ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ViewCacheInterceptor interceptor = new ViewCacheInterceptor();
        autowireCapableBeanFactory.autowireBean(interceptor);
        registry.addInterceptor(interceptor);

        AccessInterceptor accessInterceptor = new AccessInterceptor();
        autowireCapableBeanFactory.autowireBean(accessInterceptor);
        registry.addInterceptor(accessInterceptor);
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