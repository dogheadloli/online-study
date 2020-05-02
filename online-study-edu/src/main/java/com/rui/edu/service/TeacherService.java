package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.qo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 教师 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> page, TeacherQuery query);

    Map<String, Object> getTeacherList(Page<Teacher> page);

}
