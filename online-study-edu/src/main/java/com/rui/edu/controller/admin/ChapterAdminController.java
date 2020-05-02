package com.rui.edu.controller.admin;


import com.rui.common.vo.Result;
import com.rui.edu.entity.Chapter;
import com.rui.edu.service.ChapterService;
import com.rui.edu.vo.ChapterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 章节 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {

    @Autowired
    public ChapterService chapterService;

    @PostMapping()
    public Result save(@RequestBody Chapter chapter) {
        chapter.setGmtCreate(new Date());
        chapter.setGmtModified(new Date());
        chapterService.save(chapter);
        return Result.ok();
    }

    @GetMapping("{id}")
    public Result getById(@PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        Map map = new HashMap();
        map.put("item", chapter);
        return Result.ok(map);
    }

    @PutMapping("{id}")
    public Result updateById(@PathVariable String id, @RequestBody Chapter chapter) {
        chapter.setId(id);
        chapter.setGmtModified(new Date());
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result removeById(@PathVariable String id) {
        chapterService.removeChapterById(id);
        return Result.ok();
    }

    @GetMapping("nested-list/{courseId}")
    public Result nestedListByCourseId(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        Map map = new HashMap();
        map.put("items", chapterVoList);
        return Result.ok(map);
    }

}

