package com.rui.edu.service;

import com.rui.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-04-22
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

}
