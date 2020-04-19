package com.rui.common.Exception;

import lombok.Data;

/**
 * @Author: ChangRui
 * @Date: 2020/4/2
 * @Description:
 */
@Data
public class OnlineStudyException extends RuntimeException {
    private Integer code;

    public OnlineStudyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
