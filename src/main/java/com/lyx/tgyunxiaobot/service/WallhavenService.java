package com.lyx.tgyunxiaobot.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.lyx.tgyunxiaobot.client.WallhavenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author lyx
 * @createTime 2023/6/12 1:11
 */
@Service
public class WallhavenService {
    @Value("${wallhaven.key}")
    private String apiKey;

    @Autowired
    private WallhavenClient client;

    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final ExecutorService threadService = Executors.newSingleThreadExecutor();
    private final Runnable task = () -> {
        List<String> list = this.getData();
        queue.addAll(list);
    };

    public String getDefaultRandomImage() {
        if (queue.size() < 10) {
            threadService.submit(task);
        }
        try {
            return queue.poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getData() {
        Mono<String> mono = client.search(apiKey, "111", "111", "random", "desc", "1");
        String data = mono.block(Duration.ofSeconds(5L));
        return renderData(data);
    }

    private List<String> renderData(String data) {
        JSONArray jsonArray = JSONUtil.parseObj(data).getJSONArray("data");
        List<String> urls = new ArrayList<>(jsonArray.size());
        jsonArray.forEach(o -> {
            String url = JSONUtil.parseObj(o).get("path").toString();
            urls.add(url);
        });
        return urls;
    }
}
