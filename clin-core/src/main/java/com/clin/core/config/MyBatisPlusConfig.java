package com.clin.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page=new PaginationInterceptor();
        return page;
    }

//
//    @Bean
//    public GlobalConfiguration globalConfiguration() {
//        GlobalConfiguration global = new GlobalConfiguration();
//        global.setDbType("mysql");
//        return global;
//    }
//
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        page.setDialectType("mysql");
//        return page;
//    }
}
