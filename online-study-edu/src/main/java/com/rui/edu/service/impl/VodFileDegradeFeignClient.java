package com.rui.edu.service.impl;

import com.rui.common.vo.Result;
import com.rui.edu.service.VodService;
import org.springframework.stereotype.Component;

/**
 * @Author: ChangRui
 * @Date: 2020/4/19
 * @Description:
 */
@Component
public class VodFileDegradeFeignClient implements VodService {
    @Override
    public Result removeVideo(String id) {
        return Result.error(40001, "删除出错");
    }
}
