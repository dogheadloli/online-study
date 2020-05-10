package com.rui.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Blog;
import com.rui.edu.service.BlogService;
import com.rui.edu.vo.BlogVo;
import com.rui.edu.vo.BlogVo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-04-25
 */
@RestController
@RequestMapping("/eduservice/blog")
@CrossOrigin
public class BlogFrontController {

    @Autowired
    private BlogService blogService;

    @GetMapping("listBlog/{page}/{limit}")
    public Result ListBlog(@PathVariable long page, @PathVariable long limit) {
        Page<Blog> pageBlog = new Page<>(page, limit);
        Map<String, Object> map = blogService.getBlogList(pageBlog);
        return Result.ok(map);
    }

    @GetMapping("getBlog/{id}")
    public Result getBlog(@PathVariable String id) {
        BlogVo blogVo = blogService.getBlogById(id);
        Map map = new HashMap();
        map.put("item", blogVo);
        return Result.ok(map);
    }

    @PostMapping()
    public Result addBlog(@RequestBody BlogVo2 blogVo) {
        String blogId = blogService.addBlog(blogVo);
        Map map = new HashMap();
        map.put("blogId", blogId);
        return Result.ok(map);
    }

}

