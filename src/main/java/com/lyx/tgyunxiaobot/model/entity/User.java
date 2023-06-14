package com.lyx.tgyunxiaobot.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * telegram_user_id
     */
    @TableId
    @NotNull
    private Long id;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓氏
     */
    private String lastName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}