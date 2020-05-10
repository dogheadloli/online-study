package com.rui.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.FillQuestion;
import com.rui.edu.service.FillQuestionService;
import com.rui.edu.service.QuestionLibraryService;
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
@RequestMapping("/eduservice/fill-question")
@CrossOrigin
public class FillQuestionController {

    @Autowired
    private FillQuestionService questionService;
    @Autowired
    private QuestionLibraryService questionLibraryService;

    @GetMapping("/listQuestion/{page}/{limit}")
    public Result getQuestionList(@PathVariable long page, @PathVariable long limit) {
        Page<FillQuestion> fillQuestionPage = new Page<>(page, limit);
        questionService.listQuestion(fillQuestionPage);
        Map map = new HashMap();
        map.put("total", fillQuestionPage.getTotal());
        map.put("rows", fillQuestionPage.getRecords());
        return Result.ok(map);
    }

    @GetMapping("/getQuestion/{id}")
    public Result getQuestion(@PathVariable long id) {
        FillQuestion question = questionService.getById(id);
        Map map = new HashMap();
        map.put("item", question);
        return Result.ok(map);
    }

    @PostMapping("/addQuestion")
    public Result addQuestion(@RequestBody FillQuestion fillQuestion) {
        fillQuestion.setGmtCreate(new Date());
        fillQuestion.setGmtModified(new Date());
        questionService.saveQuestion(fillQuestion);
        return Result.ok();
    }

    @DeleteMapping("deleteQuestion/{id}")
    public Result deleteQuestion(@PathVariable String id) {
        questionService.removeQuestion(id);
        return Result.ok();
    }

    @PostMapping("updateQuestion")
    public Result updateQuestion(@RequestBody FillQuestion fillQuestion) {
        fillQuestion.setGmtModified(new Date());
        questionService.updateById(fillQuestion);
        return Result.ok();
    }

}

