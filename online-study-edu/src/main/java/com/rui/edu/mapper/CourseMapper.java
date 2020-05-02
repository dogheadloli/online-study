package com.rui.edu.mapper;

import com.rui.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rui.edu.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Rui
 * @since 2020-04-17
 */
public interface CourseMapper extends BaseMapper<Course> {

    CourseWebVo getBaseCourseInfo(String courseId);
}
