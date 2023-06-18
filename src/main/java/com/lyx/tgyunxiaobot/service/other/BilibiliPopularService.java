package com.lyx.tgyunxiaobot.service.other;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lyx.tgyunxiaobot.client.BilibiliPopularClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author lyx
 * @createTime 2023/6/10 17:50
 */
@Service
public class BilibiliPopularService {

    @Autowired
    private BilibiliPopularClient client;
    @Autowired
    private TemplateEngine templateEngine;

    @Cacheable(value = "bilibiliPopularRank")
    public String getPopularRank() {
        Mono<String> mono = client.getPopularRank(null, null);
        String data = mono.block();
        return renderData(data);
    }

    @CacheEvict(value = "bilibiliPopularRank", allEntries = true)
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    public void evictCache() {
        getPopularRank();
    }

    private String renderData(String data) {
        JSONObject parsedObj = JSONUtil.parseObj(data);
        JSONArray jsonArray = parsedObj.getJSONObject("data").getJSONArray("list");
        Context context = new Context();
        context.setVariable("items", jsonArray);
        return templateEngine.process("bilibiliPopularRank", context);
    }
}
