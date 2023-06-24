package com.lyx.tgyunxiaobot.client;

import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.response.ChatResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * @author lyx
 * @createTime 2023/6/23 19:39
 */
@HttpExchange(url = "https://api.openai.com/v1")
public interface OpenAiClient {

    @PostExchange(value = "/chat/completions",contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ChatResponse> chat(@RequestBody ChatRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String bearToken);
}
