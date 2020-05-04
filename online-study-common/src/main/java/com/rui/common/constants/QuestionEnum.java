package com.rui.common.constants;

import lombok.Getter;

/**
 * @Author: ChangRui
 * @Date: 2020/5/3
 * @Description:
 */
@Getter
public enum QuestionEnum {

    FILL_QUESTION(1,"填空题"),
    JUDGE_QUESTION(2,"判断题"),
    MULT_QUESTION(3,"选择题");

    private Integer code;
    private String name;

    QuestionEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
