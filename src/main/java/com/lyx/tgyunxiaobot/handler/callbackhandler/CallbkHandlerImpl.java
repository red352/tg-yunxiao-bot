package com.lyx.tgyunxiaobot.handler.callbackhandler;

import com.lyx.tgyunxiaobot.keyBoradButton.DashKeyboardButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author lyx
 * @createTime 2023/6/14 23:07
 */
@Component
public class CallbkHandlerImpl implements CallbkHandler {
    @Autowired
    private DashKeyboardButton inlineKeyboardButton;

    @Override
    public void doCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message = callbackQuery.getMessage();
        String prefix = inlineKeyboardButton.getCallBackPrefix();
        if (callbackQuery.getData().startsWith(prefix)) {
            inlineKeyboardButton.buttonTap(message, callbackQuery);
        }

    }
}
