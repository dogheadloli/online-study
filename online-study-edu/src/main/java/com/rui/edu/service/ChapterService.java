package com.rui.edu.service;

import com.rui.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.edu.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 章节 服务类
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
public interface ChapterService extends IService<Chapter> {
    public void removeChapterById(String id);

    public List<ChapterVo> nestedList(String courseId);
}
