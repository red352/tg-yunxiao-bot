package com.lyx.tgyunxiaobot.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName chat_context
 */
@TableName(value = "chat_context")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatContext implements Serializable {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 聊天模型名称
     */
    private String modelName;

    /**
     * 角色
     */
    private String role;

    /**
     * 聊天内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}