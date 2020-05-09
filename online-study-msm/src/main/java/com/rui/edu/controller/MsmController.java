package com.rui.edu.controller;

import com.rui.common.utils.RandomUtil;
import com.rui.common.vo.Result;
import com.rui.edu.service.MsmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @Author: ChangRui
 * @Date: 2020/4/21
 * @Description:
 */
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> template;

    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone) {
        // 从redis中取
        String code = template.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        } else {
            // 生成随机值，传递给阿里云
            code = RandomUtil.getFourBitRandom();
            Boolean isSend = msmService.send(code, phone);
            if (isSend) {
                template.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
                return Result.ok();
            } else {
                return Result.error(50001, "网络繁忙，请稍后再试");
            }
        }
    }
}
