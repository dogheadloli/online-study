package com.rui.edu.service;

import com.rui.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.vo.SubjectVo;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-04-11
 */
public interface SubjectService extends IService<Subject> {

    List<SubjectVo> nestedList();

}
