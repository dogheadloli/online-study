package com.rui.common.constants;

import lombok.Getter;

/**
 * @Author: ChangRui
 * @Date: 2020/4/18
 * @Description:
 */
@Getter
public enum VideoStatusEnum {
    DRAFT(0, "Draft"),
    NORMAL(1, "Normal");

    Integer code;
    String message;

    VideoStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
