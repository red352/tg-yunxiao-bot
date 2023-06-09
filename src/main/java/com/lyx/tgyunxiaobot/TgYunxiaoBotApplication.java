package com.lyx.tgyunxiaobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author lyx
 */
@SpringBootApplication
public class TgYunxiaoBotApplication {

    public static void main(String[] args) {

        SpringApplication.run(TgYunxiaoBotApplication.class, args);
    }

}
