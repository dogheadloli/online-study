package com.rui.onlinestudyoss.controller;

import com.aliyuncs.exceptions.ClientException;
import com.rui.common.Exception.OnlineStudyException;
import com.rui.common.vo.Result;
import com.rui.onlinestudyoss.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/18
 * @Description:
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/vod/video")
public class VodController {
    @Autowired
    VodService vodService;

    @PostMapping("upload")
    public Result uploadVideo(MultipartFile file) {

        String videoId = null;
        try {
            videoId = vodService.uploadVideo(file);
            Map map = new HashMap();
            map.put("videoId", videoId);
            return Result.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OnlineStudyException(20001, "上传失败");
        }

    }

    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id) {
        try {
            vodService.deleteVideo(id);
            return Result.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new OnlineStudyException(20001, "删除失败");
        }
    }

    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id) {
        String token = vodService.getPlayAuth(id);
        Map map = new HashMap();
        map.put("playAuth", token);
        return Result.ok(map);
    }
}
