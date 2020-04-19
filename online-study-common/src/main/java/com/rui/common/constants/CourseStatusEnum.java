package com.rui.common.constants;

import lombok.Getter;

/**
 * @Author: ChangRui
 * @Date: 2020/4/15
 * @Description:
 */
@Getter
public enum CourseStatusEnum {

    DRAFT(0, "Draft"),
    NORMAL(1, "Normal");

    Integer code;
    String message;

    CourseStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
