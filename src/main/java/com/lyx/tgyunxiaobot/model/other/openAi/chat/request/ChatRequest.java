package com.lyx.tgyunxiaobot.model.other.openAi.chat.request;

import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/23 20:00
 */
@Getter
@Setter
@ToString
public class ChatRequest {
    private String model;
    private List<Message> messages;
}
