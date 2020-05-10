package com.rui.edu.controller.admin;


import com.rui.common.vo.Result;
import com.rui.edu.entity.Subject;
import com.rui.edu.service.SubjectService;
import com.rui.edu.vo.OneSubject;
import com.rui.edu.vo.SubjectVo;
import com.rui.edu.vo.SubjectVo2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-04-11
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectAdminController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("list")
    public Result nestedList() {
        List<SubjectVo> subjectNestedVoList = subjectService.nestedList();
        Map map = new HashMap();
        map.put("items", subjectNestedVoList);
        return Result.ok(map);
    }

    @PostMapping("save")
    public Result save(@RequestBody SubjectVo2 subjectVo2) {
        Subject subject = new Subject();
        subject.setGmtCreate(new Date());
        subject.setGmtModified(new Date());
        subject.setLevel(subjectVo2.getParentLevel() + 1);
        subject.setSort(0);
        subject.setTitle(subjectVo2.getTitle());
        subject.setParentId(subjectVo2.getParentId());

        subjectService.save(subject);
        return Result.ok();
    }

    @PostMapping("update")
    public Result update(@RequestBody SubjectVo2 subjectVo2) {
        Subject subject = new Subject();
        subject.setId(subjectVo2.getId());
        subject.setTitle(subjectVo2.getTitle());
        subject.setGmtModified(new Date());

        subjectService.saveOrUpdate(subject);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id) {
        subjectService.deleteSubject(id);
        return Result.ok();
    }

    @GetMapping("getAllSubject")
    public Result getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        Map map = new HashMap();
        map.put("list", list);
        return Result.ok(map);
    }
}

