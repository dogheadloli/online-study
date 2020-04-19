package com.rui.edu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ChangRui
 * @Date: 2020/4/15
 * @Description:
 */
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subject;
    private String teacherName;
    private String price;//只用于显示
}