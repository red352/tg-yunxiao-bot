package com.lyx.tgyunxiaobot.config;

import com.lyx.tgyunxiaobot.client.EventsOnHistoryClient;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author lyx
 * @createTime 2023/6/9 21:42
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient client() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1048576))
                        .build())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().doOnConnected(connection ->
                                connection.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(5, TimeUnit.SECONDS)))
                ))
                .build();
    }

    @Bean
    public EventsOnHistoryClient eventsOnHistory(@Autowired WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .blockTimeout(Duration.ofSeconds(5))
                        .build();
        return httpServiceProxyFactory.createClient(EventsOnHistoryClient.class);
    }
}
