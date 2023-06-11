package com.lyx.tgyunxiaobot.handler.commandhandler;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author lyx
 * @createTime 2023/6/11 19:11
 */
public interface CommandHandler {

    void doCommand(Update update, Message msg);
}
