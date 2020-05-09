package com.rui.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.JudgeQuestion;
import com.rui.edu.service.JudgeQuestionService;
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
@RequestMapping("/edu/judge-question")
@CrossOrigin
public class JudgeQuestionController {
    @Autowired
    private JudgeQuestionService questionService;

    @GetMapping("/listQuestion/{page}/{limit}")
    public Result getQuestionList(@PathVariable long page, @PathVariable long limit) {
        Page<JudgeQuestion> judgeQuestionPage = new Page<>(page, limit);
        questionService.listQuestion(judgeQuestionPage);
        Map map = new HashMap();
        map.put("total", judgeQuestionPage.getTotal());
        map.put("rows", judgeQuestionPage.getRecords());
        return Result.ok(map);
    }

    @GetMapping("/getQuestion/{id}")
    public Result getQuestion(@PathVariable long id) {
        JudgeQuestion question = questionService.getById(id);
        Map map = new HashMap();
        map.put("item", question);
        return Result.ok(map);
    }

    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody JudgeQuestion judgeQuestion) {
        judgeQuestion.setGmtCreate(new Date());
        judgeQuestion.setGmtModified(new Date());
        questionService.saveQuestion(judgeQuestion);
        return Result.ok();
    }

    @DeleteMapping("deleteQuestion/{id}")
    public Result deleteQuestion(@PathVariable String id) {
        questionService.removeQuestion(id);
        return Result.ok();
    }

    @PostMapping("updateQuestion")
    public Result updateQuestion(@RequestBody JudgeQuestion judgeQuestion) {
        judgeQuestion.setGmtModified(new Date());
        questionService.updateById(judgeQuestion);
        return Result.ok();
    }

}

