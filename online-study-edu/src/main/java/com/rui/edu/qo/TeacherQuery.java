package com.rui.edu.qo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ChangRui
 * @Date: 2020/4/8
 * @Description:
 */
@Data
public class TeacherQuery implements Serializable {
    private static final long serialVersionUid = 1L;
    private String name;
    private String level;
    private String begin;
    private String end;
}
