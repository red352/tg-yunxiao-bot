package com.lyx.tgyunxiaobot.handler.messagehandler;

import com.lyx.tgyunxiaobot.handler.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author lyx
 * @createTime 2023/6/11 19:23
 */
@Component
@AllArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final MessageSender messageSender;

    @Override
    public void doMessage(Message msg) {
//        messageSender.copyMessage(msg.getFrom().getId(), msg.getMessageId());
    }
}
