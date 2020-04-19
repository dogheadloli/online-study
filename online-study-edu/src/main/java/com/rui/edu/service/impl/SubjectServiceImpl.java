package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rui.edu.entity.Subject;
import com.rui.edu.mapper.SubjectMapper;
import com.rui.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.vo.SubjectVo;
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

    @Override
    public List<SubjectVo> nestedList() {
        return recurrenceList("0");
    }

    private List<SubjectVo> recurrenceList(String id) {
        List<SubjectVo> subjectNestedVoList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id", id);
        List<Subject> subjectList = this.list(wrapper);
        if (subjectList == null || subjectList.isEmpty()) {
            return null;
        }
        for (Subject subject : subjectList) {
            SubjectVo subjectVo = new SubjectVo();
            subjectVo.setId(subject.getId());
            subjectVo.setValue(subject.getId());
            subjectVo.setLabel(subject.getTitle());
            subjectVo.setSubject(subject);
            List<SubjectVo> child = recurrenceList(subject.getId());
            subjectVo.setChildren(child);
            subjectNestedVoList.add(subjectVo);
        }
        return subjectNestedVoList;
    }
}
