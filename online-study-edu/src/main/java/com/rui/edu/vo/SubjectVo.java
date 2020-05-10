package com.rui.edu.vo;

import lombok.Data;


import java.util.List;

/**
 * @Author: ChangRui
 * @Date: 2020/4/11
 * @Description:
 */
@Data
public class SubjectVo {
    String id;
    String value;
    String label;
    Integer level;
    List<SubjectVo> children;
}
