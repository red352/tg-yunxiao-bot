package com.lyx.tgyunxiaobot.exception;

import com.lyx.tgyunxiaobot.handler.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lyx
 * @createTime 2023/6/25 19:31
 */
@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSender sender;

    @ExceptionHandler(Exception.class)
    public void globalException(Exception e) {
        e.printStackTrace();
    }

    @ExceptionHandler(OwnException.class)
    public void ownException(OwnException e) {
        e.printStackTrace();
        sender.sendText(e.getWho(), e.getMessage());
    }
}
