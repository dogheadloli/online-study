package com.rui.common.handler;

import com.rui.common.Exception.OnlineStudyException;
import com.rui.common.utils.ExceptionUtil;
import com.rui.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ChangRui
 * @Date: 2020/4/2
 * @Description: 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(OnlineStudyException.class)
    @ResponseBody
    public Result error(OnlineStudyException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }
}
