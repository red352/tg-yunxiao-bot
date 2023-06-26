package com.lyx.tgyunxiaobot.keyBoradButton;

import com.lyx.tgyunxiaobot.config.Bot;
import com.lyx.tgyunxiaobot.handler.MessageSender;
import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.lyx.tgyunxiaobot.service.data.ChatContextService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/26 17:12
 */
@Component
@Getter
public class ChatKeyboardButton {
    @Autowired
    private Bot bot;
    @Autowired
    private ChatContextService chatContextService;
    @Autowired
    private MessageSender messageSender;


    private final String menuText = "聊天设置";
    private final String callBackPrefix = "chat:";

    private final InlineKeyboardMarkup M1;

    public ChatKeyboardButton() {
        var chatContext = InlineKeyboardButton.builder()
                .text("查看当前聊天上下文")
                .callbackData(callBackPrefix + "context")
                .build();
        var chatContextFlush = InlineKeyboardButton.builder()
                .text("清除当前聊天上下文")
                .callbackData(callBackPrefix + "flushContext")
                .build();
        M1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(chatContext, chatContextFlush))
                .build();
    }

    public void buttonTap(Message msg, CallbackQuery callbackQuery) {
        String queryId = callbackQuery.getId();
        String data = callbackQuery.getData().substring(callBackPrefix.length());
        Long who = msg.getFrom().getId();
        AnswerCallbackQuery close = AnswerCallbackQuery.builder().callbackQueryId(queryId).build();
        switch (data) {
            case "context" -> {
                List<ChatContext> contextList = chatContextService.getChatContextList(who);
                String text = chatContextService.formatListToStringText(contextList);
                messageSender.sendText(who, text);
            }
            case "flushContext" -> {
                chatContextService.flushContext(who);
                messageSender.sendText(who, "删除成功");
            }
        }

        try {
            bot.execute(close);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
