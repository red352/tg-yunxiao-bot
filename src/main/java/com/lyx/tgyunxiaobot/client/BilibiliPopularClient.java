package com.lyx.tgyunxiaobot.client;

import jakarta.annotation.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/10 17:38
 */
@HttpExchange(url = "https://api.bilibili.com/x/web-interface/popular")
@Validated
public interface BilibiliPopularClient {
    @GetExchange
    Mono<String> getPopularRank(@Nullable @RequestParam(value = "ps", required = false) Integer ps,
                                @Nullable @RequestParam(value = "pn", required = false) Integer pn);
}
