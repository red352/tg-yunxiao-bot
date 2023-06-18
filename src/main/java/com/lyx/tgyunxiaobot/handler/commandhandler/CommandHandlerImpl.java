package com.lyx.tgyunxiaobot.handler.commandhandler;

import com.lyx.tgyunxiaobot.config.BotInfo;
import com.lyx.tgyunxiaobot.handler.MessageSender;
import com.lyx.tgyunxiaobot.keyBoradButton.DashKeyboardButton;
import com.lyx.tgyunxiaobot.model.TextMessage;
import com.lyx.tgyunxiaobot.model.entity.User;
import com.lyx.tgyunxiaobot.service.data.UserService;
import com.lyx.tgyunxiaobot.service.other.*;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.lyx.tgyunxiaobot.model.CommandCache.COMMAND_CACHE_NAME;
import static com.lyx.tgyunxiaobot.model.CommandCache.SET_CHAT;

/**
 * @author lyx
 * @createTime 2023/6/11 19:15
 */
@Component
@AllArgsConstructor
public class CommandHandlerImpl implements CommandHandler {
    private final MessageSender messageSender;
    private final BotInfo botInfo;

    private final EventOnTodayService eventOnTodayService;
    private final BingDailyImageService bingDailyImageService;
    private final BilibiliPopularService bilibiliPopularService;
    private final WallhavenService wallhavenService;
    private final DoubanService doubanService;

    private final DashKeyboardButton inlineKeyboardButton;
    private final UserService userService;
    private final CacheManager cacheManager;

    @Override
    public void doCommand(Update update, Message msg) {
        String text = msg.getText();
        if (msg.isSuperGroupMessage() || msg.isGroupMessage()) {
            doGroupCommand(text, msg);
        } else {
            doPersonalCommand(text, msg);
        }
    }

    private void doPersonalCommand(String text, Message msg) {
        handleCommand(text, msg, false);
    }

    private void doGroupCommand(String text, Message msg) {
        String[] split;
        try {
            split = text.split("@", 2);
            String botName = split[1];
            if (!botInfo.getName().equals(botName)) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        handleCommand(split[0], msg, true);
    }

    private void handleCommand(String command, Message msg, boolean sendGroup) {
        org.telegram.telegrambots.meta.api.objects.User from = msg.getFrom();
        Long who = from.getId();
        Long id = sendGroup ? msg.getChatId() : who;
        switch (command) {
            case "/dashboard" ->
                    messageSender.sendMenu(who, inlineKeyboardButton.getMenuText(), inlineKeyboardButton.getKeyboardM1());
            case "/historytoday" -> {
                String dataToday = eventOnTodayService.getDataToday(20);
                messageSender.sendHtml(id, dataToday);
            }
            case "/dailyimage" -> {
                Map<String, String> dataToday = bingDailyImageService.getDataToday();
                String sendText = dataToday.get("text");
                String sendUrl = dataToday.get("url");
                messageSender.sendPhoto(id, sendUrl, sendText);
            }
            case "/bilibilipupular" -> {
                String popularRank = bilibiliPopularService.getPopularRank();
                messageSender.sendHtml(id, popularRank);
            }
            case "/wallhavenrandom" -> {
                String url = wallhavenService.getDefaultRandomImage();
                messageSender.sendPhoto(id, url);
            }
            case "/doubannew" -> {
                Map<String, String> newMovies = doubanService.getNewMovies();
                System.out.println("newMovies = " + newMovies);
                messageSender.sendGroupPhoto(id, newMovies);
            }
            case "/doubanweek" -> {
                List<String> weeklyMovies = doubanService.getWeeklyMovies();
                System.out.println("weeklyMovies = " + weeklyMovies);
                StringBuilder text = new StringBuilder(weeklyMovies.size());
                weeklyMovies.forEach(text::append);
                messageSender.sendHtml(id, text.toString());
            }
            case "/register" -> {
                String result = userService.register(new User(who, from.getFirstName(), from.getLastName(), from.getUserName(), null, new Date()));
                messageSender.sendText(who, result);
            }
            case "/chat" -> {
                Cache cache = cacheManager.getCache(COMMAND_CACHE_NAME);
                Objects.requireNonNull(cache).put(who, SET_CHAT);
                messageSender.sendText(who, "请输入问题，输入问题后请耐心等待回复");
            }
            default -> messageSender.sendText(id, TextMessage.NO_COMMAND);
        }
    }
}
