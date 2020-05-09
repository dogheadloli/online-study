package com.rui.edu.controller;


import com.rui.common.utils.JwtUtils;
import com.rui.common.vo.Result;
import com.rui.edu.entity.UcenterMember;
import com.rui.edu.service.UcenterMemberService;
import com.rui.edu.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-04-22
 */
@RequestMapping("/educenter/member")
@CrossOrigin
@RestController
public class UcenterMemberController {

    @Autowired
    UcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public Result login(@RequestBody UcenterMember member) {
        String token = ucenterMemberService.login(member);
        Map map = new HashMap();
        map.put("token", token);
        return Result.ok(map);
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("{id}")
    public UcenterMember getMember(@PathVariable String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return ucenterMember;
    }

    @GetMapping("getMemberInfo")
    public Result getUserInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(id);
        Map map = new HashMap();
        map.put("userInfo", member);
        return Result.ok(map);
    }

}

