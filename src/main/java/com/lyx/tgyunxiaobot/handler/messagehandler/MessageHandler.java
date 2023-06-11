package com.lyx.tgyunxiaobot.handler.messagehandler;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author lyx
 * @createTime 2023/6/11 19:21
 */
public interface MessageHandler {
    void doMessage(Message msg);
}
