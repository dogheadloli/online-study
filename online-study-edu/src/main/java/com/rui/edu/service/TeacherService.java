package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.qo.TeacherQuery;

/**
 * <p>
 * 教师 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
public interface TeacherService extends IService<Teacher> {

    public void pageQuery(Page<Teacher> page, TeacherQuery query);

}
