package com.lyx.tgyunxiaobot;

import com.lyx.tgyunxiaobot.service.other.OpenAiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lyx
 * @createTime 2023/6/25 21:58
 */
@SpringBootTest
public class OpenAiTest {
    @Autowired
    private OpenAiService service;

    @Test
    void doChat(){
        String content = service.defaultChat(100L, "你好");
        System.out.println("content = " + content);
    }
}
