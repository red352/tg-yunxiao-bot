package com.lyx.tgyunxiaobot.config;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.model.DevChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author lyx
 * @createTime 2023/6/17 19:03
 */
@Configuration
public class YupiConfig {
    @Value("${yuapi.client.access-key}")
    private String accessKey;
    @Value("${yuapi.client.secret-key}")
    private String secretKey;

    @Bean
    public YuCongMingClient yuCongMingClient() {
        return new YuCongMingClient(accessKey, secretKey);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DevChatRequest devChatRequest() {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1654785040361893889L);
        return devChatRequest;
    }
}
