package com.rui.edu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChangRui
 * @Date: 2020/4/16
 * @Description:
 */
@Data
public class ChapterVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
