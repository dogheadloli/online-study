package com.rui.edu.service;

import com.rui.common.vo.Result;
import com.rui.edu.service.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ChangRui
 * @Date: 2020/4/19
 * @Description:
 */
@FeignClient(value = "online-study-oss", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodService {
    @DeleteMapping(value = "/admin/vod/video/{id}")
    public Result removeVideo(@PathVariable("id") String id);
}
