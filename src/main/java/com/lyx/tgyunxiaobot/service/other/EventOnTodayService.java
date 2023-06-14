package com.lyx.tgyunxiaobot.service.other;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyx.tgyunxiaobot.client.EventsOnHistoryClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lyx
 * @createTime 2023/6/9 23:08
 */
@Service
public class EventOnTodayService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private EventsOnHistoryClient historyClient;

    private static String getThisMonth() {
        int i = DateUtil.thisMonth() + 1;
        return formatDate(i);
    }

    private static String formatDate(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }

    private static String getThisDate() {
        int dayOfMonth = DateUtil.thisDayOfMonth();
        String day = formatDate(dayOfMonth);
        return getThisMonth() + day;
    }

    public String getDataToday(int num) {
        String thisMonth = getThisMonth();
        String thisDate = getThisDate();

        return ((EventOnTodayService) AopContext.currentProxy()).getData(thisMonth, thisDate, num);
    }

    @Cacheable(value = "eventOnDay", key = "#thisDate+#num")
    public String getData(String thisMonth, String thisDate, int num) {
        Mono<String> events = historyClient.getEventsOnDay(thisMonth);
        String block = events.block();
        return renderData(block, thisMonth, thisDate, num);
    }

    @CacheEvict(value = "eventOnDay", allEntries = true)
    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    public void evictCache() {
    }

    private String renderData(String jsonData, String thisMonth, String thisDate, int num) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, List<Map<String, Object>>>> parseData;
        try {
            parseData = mapper.readValue(jsonData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Map<String, Object>> items = parseData.get(thisMonth).get(thisDate);
        Context context = new Context();
        context.setVariable("items", items.subList(Math.max(items.size() - num, 0), items.size()));
        context.setVariable("date", thisDate);
        return templateEngine.process("data", context);
    }


}
