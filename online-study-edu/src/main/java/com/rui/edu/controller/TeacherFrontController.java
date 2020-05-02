package com.rui.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Course;
import com.rui.edu.entity.Teacher;
import com.rui.edu.service.CourseService;
import com.rui.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/22
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<Teacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherList(pageTeacher);
        return Result.ok(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {
        // 查询基本信息
        Teacher teacher = teacherService.getById(teacherId);
        // 查询课程
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("teacher_id", teacherId);
        List<Course> courseList = courseService.list(queryWrapper);

        Map map = new HashMap();
        map.put("teacher", teacher);
        map.put("courseList", courseList);
        return Result.ok(map);
    }

}
