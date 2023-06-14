package com.lyx.tgyunxiaobot.service.other;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lyx.tgyunxiaobot.client.BingDailyImageClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lyx
 * @createTime 2023/6/10 15:09
 */
@Service
public class BingDailyImageService {

    @Autowired
    private BingDailyImageClient client;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, String> getDataToday() {
        return ((BingDailyImageService) AopContext.currentProxy()).getData(0, 1, DateUtil.today());
    }

    @Cacheable(value = "imageOnDay", key = "#date")
    public Map<String, String> getData(int whichDay, int num, String date) {
        Mono<String> mono = client.getImageOnDay("js", whichDay, "zh-CN", num);
        String onDay = mono.block();
        return renderData(onDay);
    }

    @CacheEvict(value = "imageOnDay", allEntries = true)
    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void evictCache() {
    }

    private Map<String, String> renderData(String onDay) {

        JSONObject data = JSONUtil.parseObj(onDay);
        JSONArray images = data.getJSONArray("images");
        Context context = new Context();
        context.setVariable("items", images);
        String text = templateEngine.process("BingDailyImage", context);
        JSONObject jsonObject = images.getJSONObject(0);
        String url = jsonObject.get("url").toString();
        String finalUrl = urlBuilder(url);
        return Map.of("text", text, "url", finalUrl);
    }

    private String urlBuilder(String url) {
        StringBuilder base = client.baseUrl;
        return base.append(url).toString();
    }
}
