package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.edu.entity.Course;
import com.rui.edu.entity.QuestionLibrary;
import com.rui.edu.mapper.CourseMapper;
import com.rui.edu.mapper.QuestionLibraryMapper;
import com.rui.edu.service.QuestionLibraryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.vo.QuestionLibraryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class QuestionLibraryServiceImpl extends ServiceImpl<QuestionLibraryMapper, QuestionLibrary> implements QuestionLibraryService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void listQuestionLibrary(Page page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, queryWrapper);
        List<QuestionLibrary> questionLibraries = page.getRecords();
        List<QuestionLibraryVo> questionLibraryVos = new ArrayList<>();
        for (QuestionLibrary questionLibrary : questionLibraries) {
            QuestionLibraryVo questionLibraryVo = new QuestionLibraryVo();
            BeanUtils.copyProperties(questionLibrary, questionLibraryVo);
            Course course = courseMapper.selectById(questionLibrary.getCourseId());
            questionLibraryVo.setCourseName(course.getTitle());
            questionLibraryVos.add(questionLibraryVo);
        }
        page.setRecords(questionLibraryVos);
    }

    @Override
    public void addMultiCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() + 1);
        questionLibrary.setMultiQuestionNum(questionLibrary.getMultiQuestionNum() + 1);
        baseMapper.updateById(questionLibrary);
    }

    @Override
    public void addFillCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() + 1);
        questionLibrary.setFillQuestionNum(questionLibrary.getFillQuestionNum() + 1);
        baseMapper.updateById(questionLibrary);
    }

    @Override
    public void addJudgeCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() + 1);
        questionLibrary.setJudgeQuestionNum(questionLibrary.getJudgeQuestionNum() + 1);
        baseMapper.updateById(questionLibrary);
    }

    @Override
    public void reduceMultiCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() - 1);
        questionLibrary.setMultiQuestionNum(questionLibrary.getMultiQuestionNum() - 1);
        baseMapper.updateById(questionLibrary);
    }

    @Override
    public void reduceFillCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() - 1);
        questionLibrary.setFillQuestionNum(questionLibrary.getFillQuestionNum() - 1);
        baseMapper.updateById(questionLibrary);
    }

    @Override
    public void reduceJudgeCount(String id) {
        QuestionLibrary questionLibrary = baseMapper.selectById(id);
        questionLibrary.setQuestionNum(questionLibrary.getQuestionNum() - 1);
        questionLibrary.setJudgeQuestionNum(questionLibrary.getJudgeQuestionNum() - 1);
        baseMapper.updateById(questionLibrary);
    }
}
