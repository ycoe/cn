package com.duoec.web.base.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author xuwenzhen
 */
@Configuration
public class JsonConfigure {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

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
}
