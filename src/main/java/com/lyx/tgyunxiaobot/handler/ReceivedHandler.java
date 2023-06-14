package com.lyx.tgyunxiaobot.handler;

import com.lyx.tgyunxiaobot.handler.callbackhandler.CallbkHandler;
import com.lyx.tgyunxiaobot.handler.commandhandler.CommandHandler;
import com.lyx.tgyunxiaobot.handler.messagehandler.MessageHandler;
import com.lyx.tgyunxiaobot.keyBoradButton.DashKeyboardButton;
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

    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;
    private final CallbkHandler callbkHandler;

    public void handleReceived(Update update) {
        if (update.hasCallbackQuery()) {
            callbkHandler.doCallback(update);
            return;
        }
        Message msg = update.getMessage();
        if (msg.isCommand()) {
            commandHandler.doCommand(update, msg);
        } else {
            messageHandler.doMessage(msg);
        }

    }

}
