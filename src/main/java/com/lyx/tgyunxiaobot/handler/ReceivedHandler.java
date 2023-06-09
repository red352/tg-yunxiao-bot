package com.lyx.tgyunxiaobot.handler;

import com.lyx.tgyunxiaobot.keyBoradButton.MyInlineKeyboardButton;
import com.lyx.tgyunxiaobot.service.EventOnTodayService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author lyx
 * @createTime 2023/6/9 19:15
 */
@Component
public class ReceivedHandler {
    private final MessageSender messageSender;
    private final MyInlineKeyboardButton inlineKeyboardButton;
    private final EventOnTodayService eventOnTodayService;

    private boolean screaming;


    public ReceivedHandler(MessageSender messageSender, MyInlineKeyboardButton inlineKeyboardButton, EventOnTodayService eventOnTodayService) {
        this.messageSender = messageSender;
        this.inlineKeyboardButton = inlineKeyboardButton;
        this.eventOnTodayService = eventOnTodayService;
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
            case "/scream":
                screaming = true;
                break;
            case "/whisper":
                screaming = false;
                break;
            case "/dashboard":
                messageSender.sendMenu(id, inlineKeyboardButton.getMenuText(), inlineKeyboardButton.getKeyboardM1());
                break;
            case "/historytoday":
                String dataToday = eventOnTodayService.getDataToday();
                System.out.println("dataToday = " + dataToday);
                messageSender.sendHtml(id, dataToday);
                break;
        }
    }

}
