package com.lyx.tgyunxiaobot.model.other.openAi.chat;

import lombok.Getter;

/**
 * @author lyx
 * @createTime 2023/6/25 19:42
 */
@Getter
public enum ChatModel {
    GPT_3_5_TURBO("gpt-3.5-turbo");
    private final String modelName;

    ChatModel(String modelName) {
        this.modelName = modelName;
    }
}
