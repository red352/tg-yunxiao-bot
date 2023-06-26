package com.lyx.tgyunxiaobot.mapper;

import com.lyx.tgyunxiaobot.model.entity.ChatContext;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
* @author lyx
* @description 针对表【chat_context】的数据库操作Mapper
* @createDate 2023-06-25 21:10:25
* @Entity com.lyx.tgyunxiaobot.model.entity.ChatContext
*/
public interface ChatContextMapper extends BaseMapper<ChatContext> {

    Integer doDeleteContext(Long userId, String modelName);
}




