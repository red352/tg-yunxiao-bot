package com.lyx.tgyunxiaobot.keyBoradButton;

import com.lyx.tgyunxiaobot.config.Bot;
import com.lyx.tgyunxiaobot.handler.MessageSender;
import com.lyx.tgyunxiaobot.service.data.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Objects;

import static com.lyx.tgyunxiaobot.model.CommandCache.COMMAND_CACHE_NAME;
import static com.lyx.tgyunxiaobot.model.CommandCache.SET_MAIL;

/**
 * @author lyx
 * @createTime 2023/6/9 20:43
 */
@Getter
@Component
public class DashKeyboardButton {
    @Autowired
    private Bot bot;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSender messageSender;

    private final InlineKeyboardMarkup keyboardM1;
    private final InlineKeyboardMarkup keyboardM2;

    private final String menuText = "<b>关于使用云霄管家！</b>";
    private final String callBackPrefix = "Dash:";

    DashKeyboardButton() {
        var next = InlineKeyboardButton.builder().text("Next").callbackData(setCallBackData("next")).build();

        var back = InlineKeyboardButton.builder().text("Back").callbackData(setCallBackData("back")).build();

        var aboutMe = InlineKeyboardButton.builder().text("查看我的信息").callbackData(setCallBackData("aboutme")).build();

        var setmail = InlineKeyboardButton.builder()
                .text("设置邮箱").callbackData(setCallBackData("setmail"))
                .build();

        var sourceUrl = InlineKeyboardButton.builder().text("SourceCode").url("https://github.com/red352/tg-yunxiao-bot").build();

        var url = InlineKeyboardButton.builder().text("Tutorial").url("https://core.telegram.org/bots/api").build();

        keyboardM1 = InlineKeyboardMarkup.builder().keyboardRow(List.of(aboutMe, setmail)).keyboardRow(List.of(next)).build();

        //Buttons are wrapped in lists since each keyboard is a set of button rows
        keyboardM2 = InlineKeyboardMarkup.builder().keyboardRow(List.of(sourceUrl, url)).keyboardRow(List.of(back)).build();
    }

    private String setCallBackData(String next) {
        return callBackPrefix + next;
    }

    public void buttonTap(Message msg, CallbackQuery callbackQuery) {
        String queryId = callbackQuery.getId();
        String data = callbackQuery.getData().substring(callBackPrefix.length());
        Long id = msg.getChatId();
        Integer msgId = msg.getMessageId();
        EditMessageText newTxt = EditMessageText.builder().chatId(id.toString()).messageId(msgId).text("").build();
        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder().chatId(id).messageId(msgId).build();
        AnswerCallbackQuery close = AnswerCallbackQuery.builder().callbackQueryId(queryId).build();

        final Long who = callbackQuery.getFrom().getId();
        switch (data) {
            case "next" -> {
                newTxt.setText("相关链接");
                newKb.setReplyMarkup(keyboardM2);
            }
            case "back" -> {
                newTxt.setText("请选择操作");
                newKb.setReplyMarkup(keyboardM1);

            }
            case "setmail" -> {
                Cache cache = cacheManager.getCache(COMMAND_CACHE_NAME);
                Objects.requireNonNull(cache).put(who, SET_MAIL);
                newTxt.setText("请发送邮箱");
                newKb.setReplyMarkup(keyboardM1);
            }
            case "aboutme" -> {
                String aboutMe = userService.aboutMe(who);
                messageSender.sendHtml(who,aboutMe);
            }
        }
        try {
            bot.execute(close);
            bot.execute(newTxt);
            bot.execute(newKb);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
