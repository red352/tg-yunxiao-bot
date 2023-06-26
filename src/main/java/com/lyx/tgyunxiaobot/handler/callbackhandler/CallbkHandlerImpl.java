package com.lyx.tgyunxiaobot.handler.callbackhandler;

import com.lyx.tgyunxiaobot.keyBoradButton.ChatKeyboardButton;
import com.lyx.tgyunxiaobot.keyBoradButton.DashKeyboardButton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author lyx
 * @createTime 2023/6/14 23:07
 */
@Component
@AllArgsConstructor
public class CallbkHandlerImpl implements CallbkHandler {

    private DashKeyboardButton inlineKeyboardButton;
    private ChatKeyboardButton chatKeyboardButton;

    @Override
    public void doCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message = callbackQuery.getMessage();
        String callbackQueryData = callbackQuery.getData();
        if (callbackQueryData.startsWith(inlineKeyboardButton.getCallBackPrefix())) {
            inlineKeyboardButton.buttonTap(message, callbackQuery);
        } else if (callbackQueryData.startsWith(chatKeyboardButton.getCallBackPrefix())) {
            chatKeyboardButton.buttonTap(message, callbackQuery);
        }

    }
}
