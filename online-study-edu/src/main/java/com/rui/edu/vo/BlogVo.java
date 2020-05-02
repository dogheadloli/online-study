package com.rui.edu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @Author: ChangRui
 * @Date: 2020/4/25
 * @Description:
 */
@Data
public class BlogVo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String userName;

    private String subjectId;

    private String subjectName;

    private String title;

    private Long viewCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    private String description;
}
