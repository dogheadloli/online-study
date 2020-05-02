package com.rui.edu.vo;

import lombok.Data;


/**
 * @Author: ChangRui
 * @Date: 2020/4/27
 * @Description:
 */
@Data
public class BlogVo2 {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String[] subjectId;

    private String title;

    private String description;
}
