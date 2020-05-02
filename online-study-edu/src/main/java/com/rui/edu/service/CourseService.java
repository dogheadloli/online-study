package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.entity.Course;
import com.rui.edu.qo.CourseQuery;
import com.rui.edu.vo.CourseFrontVo;
import com.rui.edu.vo.CoursePublishVo;
import com.rui.edu.vo.CourseVo;
import com.rui.edu.vo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
public interface CourseService extends IService<Course> {
    public String saveCourseInfo(CourseVo courseVo);

    public CourseVo getCourseVoById(String id);

    public void updateCourseById(CourseVo courseVo);

    public void pageQuery(Page pagem, CourseQuery courseQuery);

    public void removeCourseById(String id);

    public CoursePublishVo getCoursePublishVoById(String id);

    public void publishCourseById(String id);

    Map<String, Object> getCourseFrontList(Page page, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
