package com.lyx.tgyunxiaobot.model.other.openAi.chat.response;

import com.lyx.tgyunxiaobot.model.other.openAi.chat.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lyx
 * @createTime 2023/6/23 19:53
 */
@Setter
@Getter
public class Choice {
    private Integer index;
    private Message message;
    private String finish_reason;
}
