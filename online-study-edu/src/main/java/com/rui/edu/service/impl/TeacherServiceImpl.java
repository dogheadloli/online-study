package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.edu.entity.Teacher;
import com.rui.edu.mapper.TeacherMapper;
import com.rui.edu.qo.TeacherQuery;
import com.rui.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教师 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void pageQuery(Page<Teacher> page, TeacherQuery query) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("gmt_create");
        if (query == null) {
            baseMapper.selectPage(page, queryWrapper);
            return;
        }
        if (!StringUtils.isEmpty(query.getName())) {
            queryWrapper.like("name", query.getName());
        }
        if (!StringUtils.isEmpty(query.getLevel())) {
            queryWrapper.eq("level", query.getLevel());
        }
        if (!StringUtils.isEmpty(query.getBegin())) {
            queryWrapper.ge("gmt_create", query.getBegin());
        }
        if (!StringUtils.isEmpty(query.getEnd())) {
            queryWrapper.le("gmt_create", query.getEnd());
        }
        baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Map<String, Object> getTeacherList(Page<Teacher> page) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("sort");
        baseMapper.selectPage(page, queryWrapper);

        List<Teacher> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map map = new HashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
