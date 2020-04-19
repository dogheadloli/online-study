package com.rui.edu.controller;


import com.rui.common.constants.ResultCodeEnum;
import com.rui.common.constants.UserRolesEnum;
import com.rui.common.vo.Result;
import com.rui.edu.entity.User;
import com.rui.edu.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-04-08
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/sysuser")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user.getUsername(), user.getPassword());
        if (result.getSuccess().equals(ResultCodeEnum.SUCCESS.getSuccess())) {
            Map map = new HashMap();
            map.put("token", user.getUsername());
            result.setData(map);
        }
        return result;
    }

    @GetMapping("info")
    public Result info(@RequestParam String token) {
        User user = userService.getUserByToken(token);
        if (user == null) {
            return Result.error();
        }
        Map map = new HashMap();
        map.put("roles", UserRolesEnum.getRoles(user.getRoles()));
        map.put("name", user.getUsername());
        map.put("avatar", user.getAvatar());
        return Result.ok(map);
    }

    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    public Result logout() {
        return Result.ok();
    }

}
