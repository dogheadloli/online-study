package com.rui.edu.service;

import com.rui.common.vo.Result;
import com.rui.edu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-04-08
 */
public interface UserService extends IService<User> {
    public Result login(String username, String password);

    public User getUserByToken(String token);


}
