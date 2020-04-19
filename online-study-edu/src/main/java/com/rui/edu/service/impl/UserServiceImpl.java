package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.common.vo.Result;
import com.rui.edu.entity.User;
import com.rui.edu.mapper.UserMapper;
import com.rui.edu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result login(String username, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        User user = this.getOne(wrapper);
        if (user == null || !user.getPassword().equals(password)) {
            return Result.error();
        }
        return Result.ok();
    }

    @Override
    public User getUserByToken(String token) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", token);
        User user = this.getOne(wrapper);
        return user;
    }

}
