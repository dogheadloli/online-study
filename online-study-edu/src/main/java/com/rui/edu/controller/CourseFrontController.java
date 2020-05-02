package com.rui.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Chapter;
import com.rui.edu.entity.Course;
import com.rui.edu.service.ChapterService;
import com.rui.edu.service.CourseService;
import com.rui.edu.vo.ChapterVo;
import com.rui.edu.vo.CourseFrontVo;
import com.rui.edu.vo.CourseWebVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/23
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        return Result.ok(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId) {
        // 根据id，写sql查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        Map map = new HashMap();
        map.put("courseWebVo", courseWebVo);
        map.put("chapterVideoList", chapterVoList);
        return Result.ok(map);

    }
}
