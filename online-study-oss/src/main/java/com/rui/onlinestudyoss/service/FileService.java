package com.rui.onlinestudyoss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ChangRui
 * @Date: 2020/4/10
 * @Description:
 */
public interface FileService {
    String uploadFile(MultipartFile file);

    void deleteFile(String url);
}
