package com.rui.edu.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ChangRui
 * @Date: 2020/4/16
 * @Description:
 */
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;
}
