package com.lyx.tgyunxiaobot.model.other.openAi.chat;

import lombok.*;

/**
 * @author lyx
 * @createTime 2023/6/23 19:54
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Message {
    public static final String USER = "user";
    public static final String ASSISTANT = "assistant";

    private String role;
    private String content;

    public static Message defaultMessage(String content) {
        Message message = new Message();
        message.setRole(USER);
        message.setContent(content);
        return message;
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
