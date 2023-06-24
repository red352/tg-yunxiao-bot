package com.lyx.tgyunxiaobot.model.other.openAi.chat.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/23 19:43
 */
@Getter
@Setter
public class ChatResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
