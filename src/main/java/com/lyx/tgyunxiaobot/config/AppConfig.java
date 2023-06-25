package com.lyx.tgyunxiaobot.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lyx
 * @createTime 2023/6/10 1:36
 */
@Configuration
@EnableCaching
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {


}
