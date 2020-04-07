package com.duoec.api.w.core.config;

import com.fangdd.traffic.common.mongo.core.YMongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xuwenzhen
 * @date 17/10/20
 */
@Configuration
public class BlogMongoDataSourceConfig {
    /**
     * tp_doc库
     *
     * @return
     */
    @Bean("blogMongoClient")
    @ConfigurationProperties(prefix = "mongodb.db")
    public YMongoClient getMongoClient() {
        return new YMongoClient();
    }
}
