package com.lyx.tgyunxiaobot;

import com.lyx.tgyunxiaobot.service.EventOnTodayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lyx
 * @createTime 2023/6/9 23:19
 */
@SpringBootTest
public class EventOnTodayServiceTest {
    @Autowired
    private EventOnTodayService eventOnTodayService;

    @Test
    void test() {
        String s = eventOnTodayService.getDataToday();
        System.out.println("s = " + s);
    }
}
