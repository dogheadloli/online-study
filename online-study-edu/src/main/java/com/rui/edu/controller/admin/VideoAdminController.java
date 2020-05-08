package com.rui.edu.controller.admin;


import com.rui.common.constants.VideoStatusEnum;
import com.rui.common.vo.Result;
import com.rui.edu.entity.Video;
import com.rui.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Rui
 * @since 2020-03-26
 */
@RestController
@CrossOrigin
@RequestMapping("admin/edu/video")
public class VideoAdminController {

    @Autowired
    VideoService videoService;

    @PostMapping("save-video-info")
    public Result addVideo(@RequestBody Video video) {
        video.setGmtCreate(new Date());
        video.setGmtModified(new Date());
        video.setStatus(VideoStatusEnum.DRAFT.getMessage());
        videoService.save(video);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id) {
        videoService.removeVideoById(id);
        return Result.ok();
    }

    @PutMapping("/update-video-info/{id}")
    public Result updateVideo(@PathVariable String id, @RequestBody Video video) {
        video.setGmtModified(new Date());
        videoService.updateById(video);
        return Result.ok();
    }

    @GetMapping("/video-info/{id}")
    public Result getByid(@PathVariable String id) {
        Video video = videoService.getById(id);
        Map map = new HashMap();
        map.put("item", video);
        return Result.ok(map);
    }
}

