package com.rui.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Course;
import com.rui.edu.entity.Teacher;
import com.rui.edu.service.CourseService;
import com.rui.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/21
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("index")
    public Result index() {
        QueryWrapper courseWrapper = new QueryWrapper();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseWrapper);

        QueryWrapper teacherWrapper = new QueryWrapper();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);

        Map map = new HashMap();
        map.put("eduList", courseList);
        map.put("teacherList", teacherList);

        return Result.ok(map);
    }
}
