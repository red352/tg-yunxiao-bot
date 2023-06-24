package com.lyx.tgyunxiaobot.config;

import com.lyx.tgyunxiaobot.client.*;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
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
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                                .doOnConnected(connection -> connection
                                        .addHandlerLast(new ReadTimeoutHandler(2, TimeUnit.SECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(2, TimeUnit.SECONDS))
                                )
                                .doOnError(
                                        (httpClientRequest, throwable) -> throwable.printStackTrace(),
                                        (httpClientResponse, throwable) -> throwable.printStackTrace()
                                )
                        )
                )
                .build();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client()))
                .blockTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public EventsOnHistoryClient eventsOnHistory() {
        return httpServiceProxyFactory().createClient(EventsOnHistoryClient.class);
    }

    @Bean
    public BingDailyImageClient bingDailyImageClient() {
        return httpServiceProxyFactory().createClient(BingDailyImageClient.class);
    }

    @Bean
    public BilibiliPopularClient bilibiliPopular() {
        return httpServiceProxyFactory().createClient(BilibiliPopularClient.class);
    }

    @Bean
    public WallhavenClient wallhavenClient() {
        return httpServiceProxyFactory().createClient(WallhavenClient.class);
    }

    @Bean
    public DoubanClient doubanClient() {
        return httpServiceProxyFactory().createClient(DoubanClient.class);
    }

    @Bean
    public OpenAiClient openAiClient() {
        return httpServiceProxyFactory().createClient(OpenAiClient.class);
    }
}
