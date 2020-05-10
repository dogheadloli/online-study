package com.rui.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.QuestionLibrary;
import com.rui.edu.service.FillQuestionService;
import com.rui.edu.service.JudgeQuestionService;
import com.rui.edu.service.MultiQuestionService;
import com.rui.edu.service.QuestionLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/eduservice/question-library")
@CrossOrigin
public class QuestionLibraryController {

    @Autowired
    private QuestionLibraryService questionLibraryService;
    @Autowired
    private MultiQuestionService multiQuestionService;
    @Autowired
    private FillQuestionService fillQuestionService;
    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @GetMapping("/list/{page}/{limit}")
    public Result getQuestionLibraryList(@PathVariable long page, @PathVariable long limit) {
        Page<QuestionLibrary> pageQuestionLibrary = new Page<>(page, limit);
        questionLibraryService.listQuestionLibrary(pageQuestionLibrary);
        Map map = new HashMap();
        map.put("total", pageQuestionLibrary.getTotal());
        map.put("rows", pageQuestionLibrary.getRecords());
        return Result.ok(map);
    }

    @GetMapping("/getExam/{id}")
    public Result getExam(@PathVariable long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("library_id", id);

        List multiQuestions = multiQuestionService.list(queryWrapper);
        List judgeQuestions = judgeQuestionService.list(queryWrapper);
        List fillQuestions = fillQuestionService.list(queryWrapper);

        QuestionLibrary questionLibrary = questionLibraryService.getById(id);

        Map map = new HashMap();
        map.put("multiQuestions", multiQuestions);
        map.put("judgeQuestions", judgeQuestions);
        map.put("fillQuestions", fillQuestions);
        map.put("multiQuestionNum", questionLibrary.getMultiQuestionNum());
        map.put("judgeQuestionNum", questionLibrary.getJudgeQuestionNum());
        map.put("fillQuestionNum", questionLibrary.getFillQuestionNum());
        map.put("questionNum", questionLibrary.getQuestionNum());

        return Result.ok(map);
    }
}

