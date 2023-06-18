package com.lyx.tgyunxiaobot.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/19 0:12
 */
@HttpExchange(url = "https://movie.douban.com/chart")
public interface DoubanClient {
    @GetExchange
    Mono<String> getDocumentData();

}
