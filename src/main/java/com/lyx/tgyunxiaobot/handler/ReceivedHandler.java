package com.lyx.tgyunxiaobot.handler;

import com.lyx.tgyunxiaobot.handler.commandhandler.CommandHandler;
import com.lyx.tgyunxiaobot.handler.messagehandler.MessageHandler;
import com.lyx.tgyunxiaobot.keyBoradButton.MyInlineKeyboardButton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author lyx
 * @createTime 2023/6/9 19:15
 */
@Component
@AllArgsConstructor
public class ReceivedHandler {

    private final MyInlineKeyboardButton inlineKeyboardButton;
    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;


    public void handleReceived(Update update) {
        if (update.hasCallbackQuery()) {
            doCallback(update);
            return;
        }
        Message msg = update.getMessage();
        if (msg.isCommand()) {
            commandHandler.doCommand(update, msg);
        } else {
            messageHandler.doMessage(msg);
        }

    }

    private void doCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        inlineKeyboardButton.buttonTap(callbackQuery.getMessage().getChatId(), callbackQuery.getId(), callbackQuery.getData(), callbackQuery.getMessage().getMessageId());
    }

}
