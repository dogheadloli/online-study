package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.common.Exception.OnlineStudyException;
import com.rui.common.utils.JwtUtils;
import com.rui.common.utils.MD5;
import com.rui.edu.entity.UcenterMember;
import com.rui.edu.mapper.UcenterMemberMapper;
import com.rui.edu.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.vo.RegisterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-04-22
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new OnlineStudyException(50002, "登录失败");
        }

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new OnlineStudyException(50003, "用户不存在");
        }

        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new OnlineStudyException(50004, "密码错误");
        }

        if (member.getDeleted()) {
            throw new OnlineStudyException(50005, "用户已注销");
        }

        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String nickName = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new OnlineStudyException(50006, "注册失败");
        }

        // 验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new OnlineStudyException(50007, "验证码错误");
        }

        // 是否存在
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new OnlineStudyException(50008, "用户已存在");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setDeleted(false);
        member.setGmtCreate(new Date());
        member.setGmtModified(new Date());
        member.setAvatar("https://onlline-study-file.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);
    }
}
