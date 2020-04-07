/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.duoec.api.w.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer，如果你不使用通用Mapper
 * 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
 *
 */
@Configuration
public class MyBatisConfig implements TransactionManagementConfigurer {
    @Autowired
    private DataSource dataSource;

    /**
     * 添加分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(annotationDrivenTransactionManager());
    }
}
