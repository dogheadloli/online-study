package com.rui.onlinestudyoss.controller;

import com.rui.common.vo.Result;
import com.rui.onlinestudyoss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/10
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/oss/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        String url = fileService.uploadFile(file);
        Map map = new HashMap();
        map.put("url", url);
        return Result.ok(map);
    }
}
