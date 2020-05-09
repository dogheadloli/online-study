package com.rui.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.rui.edu.entity.Blog;
import com.rui.edu.entity.BlogDescription;
import com.rui.edu.entity.Subject;
import com.rui.edu.entity.UcenterMember;
import com.rui.edu.mapper.BlogDescriptionMapper;
import com.rui.edu.mapper.BlogMapper;
import com.rui.edu.mapper.SubjectMapper;
import com.rui.edu.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.edu.service.UcenterMemberService;
import com.rui.edu.vo.BlogVo;
import com.rui.edu.vo.BlogVo2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Rui
 * @since 2020-04-25
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Autowired
    BlogDescriptionMapper blogDescriptionMapper;
    @Autowired
    UcenterMemberService ucenterMemberService;
    @Autowired
    SubjectMapper subjectMapper;

    @Override
    public Map<String, Object> getBlogList(Page page) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByAsc("gmt_create");
        baseMapper.selectPage(page, wrapper);

        List<Blog> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        List<BlogVo> blogVoList = new ArrayList<>();
        for (Blog blog : records) {
            BlogVo blogVo = new BlogVo();
            BeanUtils.copyProperties(blog, blogVo);
            UcenterMember ucenterMember = ucenterMemberService.getUcenterMember(blog.getUserId());
            blogVo.setUserName(ucenterMember.getNickname());
            String[] subjectIds = new Gson().fromJson(blog.getSubjectId(), String[].class);
            Subject subject = subjectMapper.selectById(subjectIds[subjectIds.length-1]);
            blogVo.setSubjectName(subject.getTitle());
            BlogDescription blogDescription = blogDescriptionMapper.selectById(blog.getId());
            //去除html标签
            String description = blogDescription.getDescription();
            description = description.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
            blogVo.setDescription(description);
            blogVoList.add(blogVo);
        }

        Map map = new HashMap();
        map.put("items", blogVoList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public BlogVo getBlogById(String id) {
        BlogVo blogVo = new BlogVo();
        Blog blog = baseMapper.selectById(id);
        // 访问数加一
        blog.setViewCount(blog.getViewCount() + 1);
        baseMapper.updateById(blog);

        BeanUtils.copyProperties(blog, blogVo);
        UcenterMember ucenterMember = ucenterMemberService.getUcenterMember(blog.getUserId());
        blogVo.setUserName(ucenterMember.getNickname());
        String[] subjectIds = new Gson().fromJson(blog.getSubjectId(), String[].class);
        Subject subject = subjectMapper.selectById(subjectIds[subjectIds.length-1]);
        blogVo.setSubjectName(subject.getTitle());
        BlogDescription blogDescription = blogDescriptionMapper.selectById(blog.getId());
        blogVo.setDescription(blogDescription.getDescription());
        return blogVo;
    }

    @Override
    public String addBlog(BlogVo2 blogVo) {
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVo, blog);
        blog.setSubjectId(new Gson().toJson(blogVo.getSubjectId()));
        blog.setViewCount(0L);
        blog.setGmtCreate(new Date());
        blog.setGmtModified(new Date());
        baseMapper.insert(blog);
        BlogDescription blogDescription = new BlogDescription();
        blogDescription.setDescription(blogVo.getDescription());
        blogDescription.setId(blog.getId());
        blogDescription.setGmtCreate(new Date());
        blogDescription.setGmtModified(new Date());
        blogDescriptionMapper.insert(blogDescription);
        return blog.getId();
    }
}
