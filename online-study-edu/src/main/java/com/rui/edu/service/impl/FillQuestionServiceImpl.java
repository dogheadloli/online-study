package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.constants.QuestionEnum;
import com.rui.edu.entity.Course;
import com.rui.edu.entity.FillQuestion;
import com.rui.edu.entity.QuestionLibrary;
import com.rui.edu.mapper.CourseMapper;
import com.rui.edu.mapper.FillQuestionMapper;
import com.rui.edu.mapper.QuestionLibraryMapper;
import com.rui.edu.service.FillQuestionService;
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
public class FillQuestionServiceImpl extends ServiceImpl<FillQuestionMapper, FillQuestion> implements FillQuestionService {

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
        List<FillQuestion> fillQuestionList = page.getRecords();
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (FillQuestion fillQuestion : fillQuestionList) {
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(fillQuestion, questionVo);
            QuestionLibrary questionLibrary = questionLibraryMapper.selectById(fillQuestion.getLibraryId());
            Course course = courseMapper.selectById(questionLibrary.getCourseId());
            questionVo.setCourseId(course.getId());
            questionVo.setCourseName(course.getTitle());
            questionVo.setOptionValue(QuestionEnum.FILL_QUESTION.getName());
            questionVoList.add(questionVo);
        }
        page.setRecords(questionVoList);
    }

    @Override
    @Transactional
    public void saveQuestion(FillQuestion question) {
        baseMapper.insert(question);
        questionLibraryService.addFillCount(question.getLibraryId());
    }

    @Override
    @Transactional
    public void removeQuestion(String id) {
        FillQuestion fillQuestion = baseMapper.selectById(id);
        baseMapper.deleteById(id);
        questionLibraryService.reduceFillCount(fillQuestion.getLibraryId());
    }
}
