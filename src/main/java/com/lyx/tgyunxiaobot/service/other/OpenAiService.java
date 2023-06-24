package com.lyx.tgyunxiaobot.service.other;

import com.lyx.tgyunxiaobot.client.OpenAiClient;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lyx
 * @createTime 2023/6/23 20:19
 */
@Service
public class OpenAiService {
    @Autowired
    private OpenAiClient client;
    @Value("${openai.key}")
    private String key;

    private static final ConcurrentMap<Long, ChatRequest> map = new ConcurrentHashMap<>();

    public String chat(Long id, String text) {
        ChatRequest chatRequest = map.get(id);
        Message message = Message.defaultMessage(text);
        if (chatRequest == null) {
            chatRequest = new ChatRequest();
            chatRequest.setModel("gpt-3.5-turbo");
            chatRequest.setMessages(new ArrayList<>(10));
        }
        List<Message> messages = chatRequest.getMessages();
        if (messages.size() == 10) {
            messages.clear();
        }
        messages.add(message);
        Mono<ChatResponse> chat = client.chat(chatRequest, "Bearer " + key);
        ChatResponse response = chat.block();
        Message responseMessage = Objects.requireNonNull(response).getChoices().get(0).getMessage();
        messages.add(responseMessage);
        map.put(id, chatRequest);
        return responseMessage.getContent();
    }
}
