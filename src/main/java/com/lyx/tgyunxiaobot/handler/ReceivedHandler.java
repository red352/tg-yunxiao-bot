package com.lyx.tgyunxiaobot.handler;

import com.lyx.tgyunxiaobot.keyBoradButton.MyInlineKeyboardButton;
import com.lyx.tgyunxiaobot.service.BilibiliPopularService;
import com.lyx.tgyunxiaobot.service.BingDailyImageService;
import com.lyx.tgyunxiaobot.service.EventOnTodayService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Map;

/**
 * @author lyx
 * @createTime 2023/6/9 19:15
 */
@Component
public class ReceivedHandler {
    private final MessageSender messageSender;
    private final MyInlineKeyboardButton inlineKeyboardButton;
    private final EventOnTodayService eventOnTodayService;
    private final BingDailyImageService bingDailyImageService;
    private final BilibiliPopularService bilibiliPopularService;

    /**
     * 是否大写
     */
    private boolean screaming;


    public ReceivedHandler(MessageSender messageSender, MyInlineKeyboardButton inlineKeyboardButton, EventOnTodayService eventOnTodayService, BingDailyImageService bingDailyImageService, BilibiliPopularService bilibiliPopularService) {
        this.messageSender = messageSender;
        this.inlineKeyboardButton = inlineKeyboardButton;
        this.eventOnTodayService = eventOnTodayService;
        this.bingDailyImageService = bingDailyImageService;
        this.bilibiliPopularService = bilibiliPopularService;
    }

    public void handleReceived(Update update) {
        if (update.hasCallbackQuery()) {
            doCallback(update);
            return;
        }
        Message msg = update.getMessage();
        User user = msg.getFrom();
        Long id = user.getId();
        if (msg.isCommand()) {
            doCommand(msg, id);
        } else {
            doMessage(msg, id);
        }

    }

    private void doCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        inlineKeyboardButton.buttonTap(callbackQuery.getMessage().getChatId(), callbackQuery.getId(), callbackQuery.getData(), callbackQuery.getMessage().getMessageId());
    }

    private void doMessage(Message msg, Long id) {
        if (screaming) {
            messageSender.sendText(id, msg.getText().toUpperCase());
        } else {
            messageSender.copyMessage(id, msg.getMessageId());
        }
    }

    private void doCommand(Message msg, Long id) {
        String text = msg.getText();
        switch (text) {
            case "/scream" -> screaming = true;
            case "/whisper" -> screaming = false;
            case "/dashboard" ->
                    messageSender.sendMenu(id, inlineKeyboardButton.getMenuText(), inlineKeyboardButton.getKeyboardM1());
            case "/historytoday" -> {
                String dataToday = eventOnTodayService.getDataToday();
                messageSender.sendHtml(id, dataToday);
            }
            case "/dailyimage" -> {
                Map<String, String> dataToday = bingDailyImageService.getDataToday();
                String sendText = dataToday.get("text");
                String sendUrl = dataToday.get("url");
                messageSender.sendHtml(id, sendText);
                messageSender.sendPhoto(id, sendUrl);
            }
            case "/bilibilipupular" -> {
                String popularRank = bilibiliPopularService.getPopularRank();
                messageSender.sendHtml(id,popularRank);
            }
        }
    }

}
