package com.lyx.tgyunxiaobot.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.tgyunxiaobot.mapper.ChatContextMapper;
import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.service.data.ChatContextService;
import org.springframework.stereotype.Service;

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
    public ChatRequest getChatRequestContext(Long userId, String modelName, int limit) {
        List<ChatContext> contextList = lambdaQuery()
                .select(ChatContext::getContent, ChatContext::getRole)
                .eq(ChatContext::getUserId, userId)
                .eq(ChatContext::getModelName, modelName)
                .orderByAsc(ChatContext::getCreateTime)
                .last("LIMIT  " + limit)
                .list();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel(modelName);
        List<Message> messages = contextList.stream()
                .map(chatContext -> new Message(chatContext.getRole(), chatContext.getContent()))
                .collect(Collectors.toList());
        chatRequest.setMessages(messages);
        return chatRequest;
    }

    @Override
    public void saveChatResponse(Long userId, String modelName, Message responseMessage) {
        save(ChatContext.builder()
                .content(responseMessage.getContent())
                .role(responseMessage.getRole())
                .userId(userId)
                .modelName(modelName)
                .build());
    }
}




