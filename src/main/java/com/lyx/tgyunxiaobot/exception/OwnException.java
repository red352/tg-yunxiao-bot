package com.lyx.tgyunxiaobot.exception;

import lombok.*;

/**
 * @author lyx
 * @createTime 2023/6/25 19:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OwnException extends RuntimeException {
    private Long who;
    private String message;
}
