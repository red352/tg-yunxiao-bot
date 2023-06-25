package com.lyx.tgyunxiaobot.service.data;

import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import com.lyx.tgyunxiaobot.model.other.openAi.chat.request.ChatRequest;

import java.util.List;

/**
* @author lyx
* @description 针对表【chat_context】的数据库操作Service
* @createDate 2023-06-25 21:10:25
*/
public interface ChatContextService extends IService<ChatContext> {

    ChatRequest getChatRequestContext(Long userId, String modelName, int limit);

    void saveChatResponse(Long userId, String modelName, List<Message> messages);
}
