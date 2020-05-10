package com.rui.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.MultiQuestion;
import com.rui.edu.service.MultiQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
@RestController
@RequestMapping("/eduservice/multi-question")
@CrossOrigin
public class MultiQuestionController {
    @Autowired
    private MultiQuestionService questionService;

    @GetMapping("/listQuestion/{page}/{limit}")
    public Result getQuestionList(@PathVariable long page, @PathVariable long limit) {
        Page<MultiQuestion> multiQuestionPage = new Page<>(page, limit);
        questionService.listQuestion(multiQuestionPage);
        Map map = new HashMap();
        map.put("total", multiQuestionPage.getTotal());
        map.put("rows", multiQuestionPage.getRecords());
        return Result.ok(map);
    }

    @GetMapping("/getQuestion/{id}")
    public Result getQuestion(@PathVariable long id) {
        MultiQuestion question = questionService.getById(id);
        Map map = new HashMap();
        map.put("item", question);
        return Result.ok(map);
    }

    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody MultiQuestion multiQuestion) {
        multiQuestion.setGmtCreate(new Date());
        multiQuestion.setGmtModified(new Date());
        questionService.saveQuestion(multiQuestion);
        return Result.ok();
    }

    @DeleteMapping("deleteQuestion/{id}")
    public Result deleteQuestion(@PathVariable String id) {
        questionService.removeQuestion(id);
        return Result.ok();
    }

    @PostMapping("updateQuestion")
    public Result updateQuestion(@RequestBody MultiQuestion multiQuestion) {
        multiQuestion.setGmtModified(new Date());
        questionService.updateById(multiQuestion);
        return Result.ok();
    }

}

