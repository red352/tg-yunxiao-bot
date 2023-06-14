package com.lyx.tgyunxiaobot.handler.messagehandler;

import com.lyx.tgyunxiaobot.handler.MessageSender;
import com.lyx.tgyunxiaobot.service.data.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.lyx.tgyunxiaobot.model.CommandCache.COMMAND_CACHE_NAME;
import static com.lyx.tgyunxiaobot.model.CommandCache.SET_MAIL;

/**
 * @author lyx
 * @createTime 2023/6/11 19:23
 */
@Component
@AllArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final MessageSender messageSender;
    private final CacheManager cacheManager;
    private final UserService userService;

    @Override
    public void doMessage(Message msg) {
        Long id = msg.getFrom().getId();
        Cache cache = cacheManager.getCache(COMMAND_CACHE_NAME);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(id);
            if (valueWrapper != null) {
                doCacheCommand(msg, (String) valueWrapper.get(), cache);
            }
        }
    }

    private void doCacheCommand(Message msg, String command, Cache cache) {
        if (command == null) {
            return;
        }
        if (command.equals(SET_MAIL)) {
            cache.evict(msg.getFrom().getId());
            Long who = msg.getFrom().getId();
            String mail = msg.getText();
            boolean success = userService.setMail(who, mail);
            messageSender.sendText(who, success ? "设置邮件成功" : "设置邮件失败");
        }
    }
}
