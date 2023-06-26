package com.lyx.tgyunxiaobot;

import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.lyx.tgyunxiaobot.service.data.ChatContextService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/26 19:04
 */
@SpringBootTest
public class ChatContextTest {
    @Autowired
    private ChatContextService service;
    @Test
    void test(){
        List<ChatContext> chatContextList = service.getChatContextList(1430829586L);
        System.out.println("chatContextList = " + chatContextList);
        System.out.println("service.formatListToStringText(chatContextList) = " + service.formatListToStringText(chatContextList));
    }
}
