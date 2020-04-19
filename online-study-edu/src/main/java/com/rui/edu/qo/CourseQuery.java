package com.rui.edu.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ChangRui
 * @Date: 2020/4/15
 * @Description:
 */
@Data
public class CourseQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;
}
