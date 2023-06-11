package com.lyx.tgyunxiaobot.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/12 1:24
 */
@HttpExchange(url = "https://wallhaven.cc/api/v1")
public interface WallhavenClient {

    @GetExchange("/search")
    Mono<String> search(@RequestParam(value = "apikey", required = false) String apikey,
                        @RequestParam(value = "categories", required = false) String categories,
                        @RequestParam(value = "purity", required = false) String purity,
                        @RequestParam(value = "sorting", required = false) String sorting,
                        @RequestParam(value = "order", required = false) String order,
                        @RequestParam(value = "ai_art_filter", required = false) String aiArtFilter);
}
