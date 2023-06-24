package com.lyx.tgyunxiaobot;

import com.lyx.tgyunxiaobot.client.EventsOnHistoryClient;
import com.lyx.tgyunxiaobot.client.OpenAiClient;
import com.lyx.tgyunxiaobot.client.WallhavenClient;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.response.ChatResponse;
import com.lyx.tgyunxiaobot.service.other.OpenAiService;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author lyx
 * @createTime 2023/6/9 21:34
 */
@SpringBootTest
public class HttpClientTest {

    @Autowired
    private EventsOnHistoryClient eventsOnHistoryClient;
    @Autowired
    private WallhavenClient wallhavenClient;
    @Autowired
    private OpenAiService openAiService;


    @Test
    void eventsOnHistory() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        Mono<String> rep = eventsOnHistoryClient.getEventsOnDay("09");
        rep.subscribe(
                history -> {
                    try {
                        System.out.println("history = " + history);
                    } finally {
                        count.countDown();
                    }
                },
                error -> {
                    System.err.println("An error occurred: " + error.getMessage());
                    count.countDown();
                },
                count::countDown
        );
        count.await();
    }

    @Test
    void wallhavenTest(@Value("${wallhaven.key}") String apiKey) {
        Mono<String> mono = wallhavenClient.search(apiKey, "111", "111", "random", "desc", "1");
        String block = mono.block();
        System.out.println("block = " + block);
    }

    @Test
    void chatTest() {
        String chat = openAiService.chat(1L, "你好");
        System.out.println("chat = " + chat);
    }

}
