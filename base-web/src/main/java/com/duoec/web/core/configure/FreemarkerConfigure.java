package com.duoec.web.core.configure;

import com.duoec.web.core.freemarker.XFreeMarkerConfigurer;
import com.duoec.web.core.freemarker.view.XFreeMarkerView;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 *
 * @author xuwenzhen
 * @date 2020/3/1
 */
@Configuration
public class FreemarkerConfigure {
    @Bean
    @ConfigurationProperties(prefix = "spring.freemarker")
    public FreeMarkerConfigurer getXFreeMarkerConfigurer() {
        return new XFreeMarkerConfigurer();
    }

    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setViewClass(XFreeMarkerView.class);
        return freeMarkerViewResolver;
    }
}
