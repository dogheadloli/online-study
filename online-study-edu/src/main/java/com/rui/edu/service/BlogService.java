package com.rui.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.entity.Blog;
import com.rui.edu.vo.BlogVo;
import com.rui.edu.vo.BlogVo2;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-04-25
 */
public interface BlogService extends IService<Blog> {

    Map<String, Object> getBlogList(Page page);

    BlogVo getBlogById(String id);

    String addBlog(BlogVo2 blogVo);

}
