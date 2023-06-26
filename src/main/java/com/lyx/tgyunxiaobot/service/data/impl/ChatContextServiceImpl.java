package com.lyx.tgyunxiaobot.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.tgyunxiaobot.mapper.ChatContextMapper;
import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.service.data.ChatContextService;
import com.lyx.tgyunxiaobot.service.other.OpenAiService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lyx
 * @description 针对表【chat_context】的数据库操作Service实现
 * @createDate 2023-06-25 21:10:25
 */
@Service
public class ChatContextServiceImpl extends ServiceImpl<ChatContextMapper, ChatContext>
        implements ChatContextService {

    @Override
    public ChatRequest getChatRequestContext(Long userId, @Nullable String modelName, int limit) {
        List<ChatContext> contextList = getChatContextList(userId, modelName, limit);
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel(modelName);
        List<Message> messages = contextList.stream()
                .map(chatContext -> new Message(chatContext.getRole(), chatContext.getContent()))
                .collect(Collectors.toList());
        chatRequest.setMessages(messages);
        return chatRequest;
    }

    @Override
    public List<ChatContext> getChatContextList(Long userId, String modelName, int limit) {
        return lambdaQuery()
                .select(ChatContext::getContent, ChatContext::getRole)
                .eq(ChatContext::getUserId, userId)
                .eq(StringUtils.hasText(modelName), ChatContext::getModelName, modelName)
                .orderByAsc(ChatContext::getCreateTime)
                .last("LIMIT  " + limit)
                .list();
    }

    @Override
    public List<ChatContext> getChatContextList(Long userId) {
        return getChatContextList(userId, OpenAiService.defaultModel, OpenAiService.maxContextLength - 1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveChatResponse(Long userId, String modelName, List<Message> messages) {
        List<ChatContext> chatContextList = messages.stream().map(message -> ChatContext.builder()
                .content(message.getContent())
                .role(message.getRole())
                .userId(userId)
                .modelName(modelName)
                .build()).toList();
        saveBatch(chatContextList);
    }

    @Override
    public String formatListToStringText(List<ChatContext> contextList) {
        StringBuilder stringBuilder = new StringBuilder();
        contextList.forEach(chatContext -> {
            stringBuilder.append(chatContext.getModelName()).append(" ")
                    .append(chatContext.getRole()).append(" ")
                    .append(chatContext.getContent()).append(" ")
                    .append(chatContext.getCreateTime())
                    .append(System.lineSeparator());
        });
        return stringBuilder.toString();
    }

    @Override
    public boolean doDeleteContext(Long userId, @Nullable String modelName) {
        Integer lines = baseMapper.doDeleteContext(userId, modelName);
        return lines > 0;
    }

    @Override
    public boolean flushContext(Long userId) {
        return doDeleteContext(userId, OpenAiService.defaultModel);
    }

}




