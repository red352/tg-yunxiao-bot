package com.lyx.tgyunxiaobot.service.data;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author lyx
 * @description 针对表【chat_context】的数据库操作Service
 * @createDate 2023-06-25 21:10:25
 */
public interface ChatContextService extends IService<ChatContext> {

    ChatRequest getChatRequestContext(Long userId, @Nullable String modelName, int limit);

    List<ChatContext> getChatContextList(Long userId, @Nullable String modelName, int limit);

    List<ChatContext> getChatContextList(Long userId);

    void saveChatResponse(Long userId, String modelName, List<Message> messages);

    String formatListToStringText(List<ChatContext> contextList);

    boolean doDeleteContext(Long userId, @Nullable String modelName);

    boolean flushContext(Long userId);
}
