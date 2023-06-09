package com.lyx.tgyunxiaobot.client;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/9 21:29
 */
@HttpExchange(url = "https://baike.baidu.com/cms/home/eventsOnHistory")
@Validated
public interface EventsOnHistoryClient {
    @GetExchange("/{day}.json")
    Mono<String> getEventsOnDay(@PathVariable @Size(max = 2, min = 2,message = "区间介于01-31") String day);
}
