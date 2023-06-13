package com.lyx.tgyunxiaobot.config;

import com.lyx.tgyunxiaobot.handler.ReceivedHandler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author lyx
 * @createTime 2023/6/9 16:42
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final ReceivedHandler receivedHandler;
    private final BotInfo info;

    private final ExecutorService threadService = Executors.newFixedThreadPool(3);

    public Bot(@Lazy ReceivedHandler receivedHandler, BotInfo info) {
        super(info.getToken());
        this.receivedHandler = receivedHandler;
        this.info = info;
    }

    public void onUpdateReceived(Update update) {
        Runnable runnable = () -> receivedHandler.handleReceived(update);
        threadService.submit(runnable);
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
