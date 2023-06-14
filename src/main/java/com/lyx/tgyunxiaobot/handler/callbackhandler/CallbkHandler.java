package com.lyx.tgyunxiaobot.handler.callbackhandler;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author lyx
 * @createTime 2023/6/14 23:06
 */
public interface CallbkHandler {
    void doCallback(Update update);
}
