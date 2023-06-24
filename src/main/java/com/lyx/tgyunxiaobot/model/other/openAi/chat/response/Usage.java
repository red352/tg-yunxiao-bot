package com.lyx.tgyunxiaobot.model.other.openAi.chat.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lyx
 * @createTime 2023/6/23 19:48
 */
@Getter
@Setter
public class Usage {
    private Integer prompt_tokens;
    private Integer completion_tokens;
    private Integer total_tokens;
}
