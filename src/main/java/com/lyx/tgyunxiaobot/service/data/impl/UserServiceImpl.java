package com.lyx.tgyunxiaobot.service.data.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.tgyunxiaobot.mapper.UserMapper;
import com.lyx.tgyunxiaobot.model.entity.User;
import com.lyx.tgyunxiaobot.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author lyx
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-06-14 20:21:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String register(User user) {
        boolean save;
        try {
            save = save(user);
        } catch (Exception e) {
            return "注册失败，请确保没有注册过";
        }
        return save ? "注册成功！" : "已注册";
    }

    @Override
    public String aboutMe(Long who) {
        User user = getById(who);
        if (user == null) return "请先注册";
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("userInfo", context);
    }

    @Override
    public boolean setMail(Long who, String mail) {
        return updateById(new User(who, null, null, null, mail, null));
    }
}




