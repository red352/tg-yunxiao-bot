package com.lyx.tgyunxiaobot.service.other;

import com.lyx.tgyunxiaobot.client.OpenAiClient;
import com.lyx.tgyunxiaobot.exception.OwnException;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.response.ChatResponse;
import com.lyx.tgyunxiaobot.service.data.ChatContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.lyx.tgyunxiaobot.model.TextMessage.CHAT_RESPONSE_ERROR;
import static com.lyx.tgyunxiaobot.model.other.openAi.chat.ChatModel.GPT_3_5_TURBO;

/**
 * @author lyx
 * @createTime 2023/6/23 20:19
 */
@Service
public class OpenAiService {
    @Autowired
    private OpenAiClient client;
    @Autowired
    private ChatContextService chatContextService;
    @Value("${openai.key}")
    private String key;

    public static final String defaultModel = GPT_3_5_TURBO.getModelName();
    public static final Integer maxContextLength = 10;

    public String defaultChat(Long id, String text) {
        // TODO: 2023/6/25 获取聊天上下文
        ChatRequest chatRequest = getChatRequestContext(id, defaultModel);
        List<Message> messages = chatRequest.getMessages();
        Message requestMessage = Message.defaultMessage(text);
        messages.add(requestMessage);
        // TODO: 2023/6/25 请求chatAi
        Mono<ChatResponse> chat = client.chat(chatRequest, "Bearer " + key);
        ChatResponse response = chat.blockOptional().orElseThrow(() -> new OwnException(id, CHAT_RESPONSE_ERROR));
        Message responseMessage = response.getChoices().get(0).getMessage();
        // TODO: 2023/6/25 保存 responseMessage 到上下文
        chatContextService.saveChatResponse(id, defaultModel, List.of(requestMessage,responseMessage));
        return responseMessage.getContent();
    }

    private ChatRequest getChatRequestContext(Long id, String modelName) {
        ChatRequest chatRequest = chatContextService.getChatRequestContext(id, modelName, maxContextLength - 1);
        if (chatRequest == null) {
            chatRequest = new ChatRequest();
            chatRequest.setModel(modelName);
            chatRequest.setMessages(new ArrayList<>());
        }
        return chatRequest;
    }

}
