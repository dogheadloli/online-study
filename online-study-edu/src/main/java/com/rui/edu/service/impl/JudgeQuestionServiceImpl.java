package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.constants.QuestionEnum;
import com.rui.edu.entity.Course;
import com.rui.edu.entity.JudgeQuestion;
import com.rui.edu.entity.QuestionLibrary;
import com.rui.edu.mapper.CourseMapper;
import com.rui.edu.mapper.JudgeQuestionMapper;
import com.rui.edu.mapper.QuestionLibraryMapper;
import com.rui.edu.service.JudgeQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.service.QuestionLibraryService;
import com.rui.edu.vo.QuestionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-05-02
 */
@Service
public class JudgeQuestionServiceImpl extends ServiceImpl<JudgeQuestionMapper, JudgeQuestion> implements JudgeQuestionService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private QuestionLibraryMapper questionLibraryMapper;
    @Autowired
    private QuestionLibraryService questionLibraryService;

    @Override
    public void listQuestion(Page page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, queryWrapper);
        List<JudgeQuestion> judgeQuestionList = page.getRecords();
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (JudgeQuestion judgeQuestion : judgeQuestionList) {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(judgeQuestion, questionVo);
            QuestionLibrary questionLibrary = questionLibraryMapper.selectById(judgeQuestion.getLibraryId());
            Course course = courseMapper.selectById(questionLibrary.getCourseId());
            questionVo.setCourseId(course.getId());
            questionVo.setCourseName(course.getTitle());
            questionVo.setOptionValue(QuestionEnum.JUDGE_QUESTION.getName());
            questionVoList.add(questionVo);
        }
        page.setRecords(questionVoList);
    }

    @Override
    @Transactional
    public void saveQuestion(JudgeQuestion question) {
        baseMapper.insert(question);
        questionLibraryService.addJudgeCount(question.getLibraryId());
    }

    @Override
    @Transactional
    public void removeQuestion(String id) {
        JudgeQuestion fillQuestion = baseMapper.selectById(id);
        baseMapper.deleteById(id);
        questionLibraryService.reduceJudgeCount(fillQuestion.getLibraryId());
    }
}
