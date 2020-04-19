package com.rui.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Teacher;
import com.rui.edu.qo.TeacherQuery;
import com.rui.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教师 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 获取所有教师
     *
     * @return
     */
    @GetMapping
    public Result list() {
        List list = teacherService.list(null);
        Map map = new HashMap();
        map.put("items", list);
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
        teacherService.removeById(id);
        return Result.ok();
    }

    /**
     * 分页条件查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result pageList(@PathVariable Long page, @PathVariable Long limit, TeacherQuery query) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherService.pageQuery(teacherPage, query);
        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", records);
        return Result.ok(map);
    }

    /**
     * 增加
     *
     * @param teacher
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Teacher teacher) {
        teacher.setGmtCreate(new Date());
        teacher.setGmtModified(new Date());
        teacherService.save(teacher);
        return Result.ok();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result getById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        Map map = new HashMap();
        map.put("item", teacher);
        return Result.ok(map);
    }

    /**
     * 修改
     *
     * @param id
     * @param teacher
     * @return
     */
    @PutMapping("{id}")
    public Result updateById(@PathVariable String id, @RequestBody Teacher teacher) {
        teacher.setId(id);
        teacher.setGmtModified(new Date());
        teacherService.updateById(teacher);
        return Result.ok();
    }
}


