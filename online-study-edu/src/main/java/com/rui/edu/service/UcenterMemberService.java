package com.rui.edu.service;

import com.rui.edu.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ChangRui
 * @Date: 2020/5/2
 * @Description:
 */
@FeignClient(value = "online-study-ucenter")
@Component
public interface UcenterMemberService {
    @GetMapping(value = "/educenter/member/{id}")
    UcenterMember getUcenterMember(@PathVariable("id") String id);
}
