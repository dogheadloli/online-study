package com.rui.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.qo.CourseQuery;
import com.rui.edu.service.CourseService;
import com.rui.edu.vo.CoursePublishVo;
import com.rui.edu.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

    @Autowired
    private CourseService courseService;

    /**
     * 新增课程
     *
     * @param courseVo
     * @return
     */
    @PostMapping("save-course-info")
    public Result saveInfo(
            @RequestBody CourseVo courseVo) {
        String courseId = courseService.saveCourseInfo(courseVo);
        Map map = new HashMap();
        map.put("courseId", courseId);
        return Result.ok(map);
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @GetMapping("course-info/{id}")
    public Result getById(@PathVariable String id) {
        CourseVo courseVo = courseService.getCourseVoById(id);
        Map map = new HashMap();
        map.put("item", courseVo);
        return Result.ok(map);
    }

    /**
     * 根据id更新课程
     *
     * @param courseVo
     * @param id
     * @return
     */
    @PutMapping("update-course-info/{id}")
    public Result updateCourseInfoById(@RequestBody CourseVo courseVo, @PathVariable String id) {
        courseService.updateCourseById(courseVo);
        return Result.ok();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result pageQuery(@PathVariable Long page, @PathVariable Long limit, CourseQuery courseQuery) {
        Page pageParam = new Page(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        long total = pageParam.getTotal();
        List records = pageParam.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return Result.ok(map);

    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result removeById(@PathVariable String id) {
        courseService.removeCourseById(id);
        return Result.ok();
    }

    /**
     * 获取课程发布信息
     *
     * @param id
     * @return
     */
    @GetMapping("course-publish-info/{id}")
    public Result getCoursePublishVoById(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(id);
        Map map = new HashMap();
        map.put("item", coursePublishVo);
        return Result.ok(map);
    }

    /**
     * 发布课程
     *
     * @param id
     * @return
     */
    @PutMapping("publish-course/{id}")
    public Result publishCourseById(@PathVariable String id) {
        courseService.publishCourseById(id);
        return Result.ok();
    }
}

