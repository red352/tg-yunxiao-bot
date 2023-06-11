package com.lyx.tgyunxiaobot;

import com.lyx.tgyunxiaobot.client.EventsOnHistoryClient;
import com.lyx.tgyunxiaobot.client.WallhavenClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

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
    void wallhavenTest(){
        Mono<String> mono = wallhavenClient.search("n4PNdLBeXbzdlbaT1ztCXd9cFD5jM9q7", "111", "111", "random", "desc", "1");
        String block = mono.block();
        System.out.println("block = " + block);
    }

}
