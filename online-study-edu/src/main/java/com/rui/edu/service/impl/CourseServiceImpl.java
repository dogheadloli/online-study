package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.rui.common.constants.CourseStatusEnum;
import com.rui.edu.entity.*;
import com.rui.edu.mapper.*;
import com.rui.edu.qo.CourseQuery;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.service.CourseService;
import com.rui.edu.service.VodService;
import com.rui.edu.vo.CourseFrontVo;
import com.rui.edu.vo.CoursePublishVo;
import com.rui.edu.vo.CourseVo;
import com.rui.edu.vo.CourseWebVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private QuestionLibraryMapper questionLibraryMapper;
    @Autowired
    private VodService vodService;

    @Override
    public String saveCourseInfo(CourseVo courseVo) {
        //保存课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        course.setSubjectId(new Gson().toJson(courseVo.getSubjectId()));
        course.setStatus(CourseStatusEnum.DRAFT.getMessage());
        course.setGmtCreate(new Date());
        course.setGmtModified(new Date());
        baseMapper.insert(course);

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        courseDescription.setId(course.getId());//设置一对一关联id
        courseDescription.setGmtCreate(new Date());
        courseDescription.setGmtModified(new Date());
        courseDescriptionMapper.insert(courseDescription);

        //为课程创建一个题库
        QuestionLibrary questionLibrary = new QuestionLibrary();
        questionLibrary.setCourseId(course.getId());
        questionLibrary.setFillQuestionNum(0L);
        questionLibrary.setJudgeQuestionNum(0L);
        questionLibrary.setMultiQuestionNum(0L);
        questionLibrary.setQuestionNum(0L);
        questionLibrary.setGmtCreate(new Date());
        questionLibrary.setGmtModified(new Date());
        questionLibraryMapper.insert(questionLibrary);

        return course.getId();
    }

    @Override
    public CourseVo getCourseVoById(String id) {
        Course course = baseMapper.selectById(id);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course, courseVo);
        BeanUtils.copyProperties(courseDescription, courseVo);
        courseVo.setSubjectId(new Gson().fromJson(course.getSubjectId(), String[].class));
        return courseVo;
    }

    @Override
    public void updateCourseById(CourseVo courseVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseVo, course);
        course.setSubjectId(new Gson().toJson(courseVo.getSubjectId()));
        course.setGmtModified(new Date());
        baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseVo.getDescription());
        courseDescription.setGmtModified(new Date());
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public void pageQuery(Page page, CourseQuery courseQuery) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("gmt_create");

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public void removeCourseById(String id) {
        // 删阿里云oss视频
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id", id);
        List<Video> list = videoMapper.selectList(queryWrapper);
        if (list != null && list.size() > 0) {
            for (Video video : list) {
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    vodService.removeVideo(videoSourceId);
                }
            }
        }

        // 删数据库视频
        QueryWrapper videoQueryWrapper = new QueryWrapper();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        // 删章节
        QueryWrapper chapterQueryWrapper = new QueryWrapper();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        // 删详情
        courseDescriptionMapper.deleteById(id);
        // 删课程
        baseMapper.deleteById(id);
        // 删题库
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper.eq("course_id", id);
        questionLibraryMapper.delete(queryWrapper);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        Course course = baseMapper.selectById(id);
        CoursePublishVo coursePublishVo = new CoursePublishVo();
        BeanUtils.copyProperties(course, coursePublishVo);
        coursePublishVo.setPrice(course.getPrice().toString());

        QueryWrapper teacherWrapper = new QueryWrapper();
        teacherWrapper.eq("id", course.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(teacherWrapper);

        String[] subjectIds = new Gson().fromJson(course.getSubjectId(), String[].class);
        QueryWrapper subjectWrapper = new QueryWrapper();
        subjectWrapper.eq("id", subjectIds[subjectIds.length - 1]);
        Subject subject = subjectMapper.selectOne(subjectWrapper);

        coursePublishVo.setTeacherName(teacher.getName());
        coursePublishVo.setSubject(subject.getTitle());

        return coursePublishVo;
    }

    @Override
    public void publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(CourseStatusEnum.NORMAL.getMessage());
        course.setGmtModified(new Date());
        baseMapper.updateById(course);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page page, CourseFrontVo courseFrontVo) {
        QueryWrapper wrapper = new QueryWrapper();


        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.like("subject_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.like("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(page, wrapper);

        List<Course> records = page.getRecords();
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

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
