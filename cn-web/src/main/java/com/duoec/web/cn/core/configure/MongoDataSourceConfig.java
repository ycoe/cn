package com.duoec.web.cn.core.configure;

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
public class MongoDataSourceConfig {
    /**
     * tp_doc库
     *
     * @return
     */
    @Bean("mongoClient")
    @ConfigurationProperties(prefix = "mongodb.db")
    public YMongoClient getMongoClient() {
        return new YMongoClient();
    }
}
