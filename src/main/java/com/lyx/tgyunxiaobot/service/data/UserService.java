package com.lyx.tgyunxiaobot.service.data;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.tgyunxiaobot.model.entity.User;

/**
 * @author lyx
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-06-14 20:21:42
 */
public interface UserService extends IService<User> {

    String register(User user);

    String aboutMe(Long who);

    boolean setMail(Long who, String mail);
}
