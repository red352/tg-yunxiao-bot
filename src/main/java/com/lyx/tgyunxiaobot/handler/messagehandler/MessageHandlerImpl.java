package com.lyx.tgyunxiaobot.handler.messagehandler;

import com.lyx.tgyunxiaobot.handler.MessageSender;
import com.lyx.tgyunxiaobot.service.data.UserService;
import com.lyx.tgyunxiaobot.service.other.OpenAiService;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.lyx.tgyunxiaobot.model.CommandCache.*;

/**
 * @author lyx
 * @createTime 2023/6/11 19:23
 */
@Component
@AllArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final MessageSender messageSender;
    private final CacheManager cacheManager;
    private final UserService userService;
    private final YuCongMingClient chatClient;
    private final DevChatRequest yupiChatRequest;
    private final OpenAiService openAiService;

    @Override
    public void doMessage(Message msg) {
        Long id = msg.getFrom().getId();
        Cache cache = cacheManager.getCache(COMMAND_CACHE_NAME);
        if (cache != null) {
            String command = cache.get(id, String.class);
            doCacheCommand(msg, command, cache);
        }

    }

    private void doCacheCommand(Message msg, String command, Cache cache) {
        if (command == null) {
            return;
        }
        Long who = msg.getFrom().getId();
        String text = msg.getText();
        switch (command) {
            case SET_MAIL -> {
                cache.evict(who);
                boolean success = userService.setMail(who, text);
                messageSender.sendText(who, success ? "设置邮件成功" : "设置邮件失败");
            }
            case SET_CHAT_YUPI -> {
                yupiChatRequest.setMessage(text);
                BaseResponse<DevChatResponse> response = chatClient.doChat(yupiChatRequest);
                if (response.getCode() == 0) {
                    messageSender.sendText(who, response.getData().getContent());
                } else {
                    messageSender.sendText(who, "暂时无法回答，请稍后再试！");
                }
            }
            case SET_CHAT -> {
                String chat = openAiService.chat(who, text);
                messageSender.sendText(who, chat);
            }
            default -> {
            }
        }
    }
}
