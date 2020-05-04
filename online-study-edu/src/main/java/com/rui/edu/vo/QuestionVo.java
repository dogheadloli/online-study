package com.rui.edu.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @Author: ChangRui
 * @Date: 2020/5/2
 * @Description:
 */
@Data
public class QuestionVo {
    String id;
    String libraryId;
    String courseId;
    String courseName;
    String question;
    String optionValue;
    Date gmtCreate;
    Date gmtModified;
}
