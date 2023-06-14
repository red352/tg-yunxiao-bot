package com.lyx.tgyunxiaobot.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lyx
 * @createTime 2023/6/14 20:32
 */
@Configuration
@MapperScan(basePackages = {"com.lyx.tgyunxiaobot.mapper"})
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor interceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
