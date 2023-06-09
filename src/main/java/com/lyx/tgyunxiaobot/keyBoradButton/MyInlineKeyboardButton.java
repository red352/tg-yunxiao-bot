package com.lyx.tgyunxiaobot.keyBoradButton;

import com.lyx.tgyunxiaobot.config.Bot;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/9 20:43
 */
@Getter
@Component
public class MyInlineKeyboardButton {
    @Autowired
    private Bot bot;

    private final InlineKeyboardMarkup keyboardM1;
    private final InlineKeyboardMarkup keyboardM2;

    private final String menuText = "<b>关于使用云霄管家！</b>";

    MyInlineKeyboardButton() {
        var next = org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder()
                .text("Next").callbackData("next")
                .build();

        var back = org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder()
                .text("Back").callbackData("back")
                .build();

        var url = org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api")
                .build();

        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(url))
                .keyboardRow(List.of(next)).build();

        //Buttons are wrapped in lists since each keyboard is a set of button rows
        keyboardM2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .build();
    }

    public void buttonTap(Long id, String queryId, String data, int msgId) {

        EditMessageText newTxt = EditMessageText.builder()
                .chatId(id.toString())
                .messageId(msgId).text("").build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(id.toString()).messageId(msgId).build();

        if (data.equals("next")) {
            newTxt.setText("菜单2");
            newKb.setReplyMarkup(keyboardM2);
        } else if (data.equals("back")) {
            newTxt.setText("菜单1");
            newKb.setReplyMarkup(keyboardM1);
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();

        try {
            bot.execute(close);
            bot.execute(newTxt);
            bot.execute(newKb);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
