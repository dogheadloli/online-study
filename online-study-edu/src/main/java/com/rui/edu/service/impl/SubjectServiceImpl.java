package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.common.Exception.OnlineStudyException;
import com.rui.edu.entity.Subject;
import com.rui.edu.mapper.CourseMapper;
import com.rui.edu.mapper.SubjectMapper;
import com.rui.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.vo.OneSubject;
import com.rui.edu.vo.SubjectVo;
import com.rui.edu.vo.TwoSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-04-11
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<SubjectVo> nestedList() {
        return recurrenceList("0");
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> oneSubjectList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("level", 0);
        List<Subject> subjectList1 = baseMapper.selectList(wrapper);
        for (Subject subject : subjectList1) {
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());
            QueryWrapper wrapper2 = new QueryWrapper();
            wrapper2.eq("parent_id", subject.getId());
            List<Subject> subjectList2 = baseMapper.selectList(wrapper2);
            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (Subject subject1 : subjectList2) {
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(subject1.getId());
                twoSubject.setTitle(subject1.getTitle());
                twoSubjectList.add(twoSubject);
            }
            oneSubject.setChildren(twoSubjectList);
            oneSubjectList.add(oneSubject);
        }

        return oneSubjectList;
    }

    @Override
    public void deleteSubject(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("subject_id", id);
        List courseList = courseMapper.selectList(queryWrapper);
        if (!courseList.isEmpty()) {
            throw new OnlineStudyException(50010, "请先删除分类下所有课程");
        }
        baseMapper.deleteById(id);
    }

    private List<SubjectVo> recurrenceList(String id) {
        List<SubjectVo> subjectNestedVoList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        wrapper.orderByDesc("gmt_create");
        List<Subject> subjectList = this.list(wrapper);
        if (subjectList == null || subjectList.isEmpty()) {
            return null;
        }
        for (Subject subject : subjectList) {
            SubjectVo subjectVo = new SubjectVo();
            subjectVo.setId(subject.getId());
            subjectVo.setValue(subject.getId());
            subjectVo.setLabel(subject.getTitle());
            // subjectVo.setSubject(subject);
            List<SubjectVo> child = recurrenceList(subject.getId());
            subjectVo.setChildren(child);
            subjectNestedVoList.add(subjectVo);
        }
        return subjectNestedVoList;
    }
}
