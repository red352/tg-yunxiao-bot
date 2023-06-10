package com.lyx.tgyunxiaobot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lyx
 * @createTime 2023/6/10 19:13
 */
@Component
@ConfigurationProperties(prefix = "tg.bot")
@Getter
@Setter
public class BotInfo {
    private String name;
    private String token;
}
