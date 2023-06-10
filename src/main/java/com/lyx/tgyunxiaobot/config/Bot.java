package com.lyx.tgyunxiaobot.config;

import com.lyx.tgyunxiaobot.handler.ReceivedHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


/**
 * @author lyx
 * @createTime 2023/6/9 16:42
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final ReceivedHandler receivedHandler;
    private final BotInfo info;

    public Bot(@Lazy ReceivedHandler receivedHandler, BotInfo info) {
        super(info.getToken());
        this.receivedHandler = receivedHandler;
        this.info = info;
    }

    public void onUpdateReceived(Update update) {
        receivedHandler.handleReceived(update);
    }

    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public String getBotUsername() {
        return info.getName();
    }

    public void onRegister() {
        super.onRegister();
    }
}
