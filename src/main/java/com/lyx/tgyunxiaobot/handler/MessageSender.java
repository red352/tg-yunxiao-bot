package com.lyx.tgyunxiaobot.handler;

import com.lyx.tgyunxiaobot.config.Bot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lyx
 * @createTime 2023/6/9 18:58
 */
@Component
@AllArgsConstructor
public class MessageSender {
    private Bot bot;

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            bot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    public void sendHtml(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .parseMode(ParseMode.HTML)
                .text(what).build();    //Message content
        try {
            bot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

    public void copyMessage(Long who, Integer msgId) {
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())  //We copy from the user
                .chatId(who.toString())      //And send it back to him
                .messageId(msgId)            //Specifying what message
                .build();
        try {
            bot.execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode(ParseMode.HTML).text(txt)
                .replyMarkup(kb).build();
        try {
            bot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendImageMarkdown(Long who, String imageUrl, String desc) {
        String md = imageMarkdownBuilder(imageUrl, desc);
        sendMarkdown(who, md);
    }

    private String imageMarkdownBuilder(String imageUrl, String desc) {
        String md = """
                ![%s](%s)
                """;
        return md.formatted(desc, imageUrl);
    }

    public void sendMarkdown(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode(ParseMode.MARKDOWNV2)
                .text(what).build();
        try {
            bot.execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPhoto(Long who, String url) {
        sendPhoto(who, url, null);
    }

    public void sendPhoto(Long who, String url, String html) {
        SendPhoto sendPhoto = SendPhoto.builder()
                .caption(html)
                .parseMode(ParseMode.HTML)
                .photo(new InputFile(url))
                .chatId(who)
                .build();
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendGroupPhoto(Long id, Map<String, String> newMovies) {
        List<InputMedia> mediaList = new ArrayList<>(newMovies.size());
        newMovies.forEach((key, value) -> {
            InputMediaPhoto photo = InputMediaPhoto.builder()
                    .media(key)
                    .caption(value)
                    .parseMode(ParseMode.HTML)
                    .build();
            mediaList.add(photo);
        });
        SendMediaGroup group = SendMediaGroup.builder()
                .chatId(id)
                .medias(mediaList)
                .build();
        try {
            bot.execute(group);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
