package com.rui.onlinestudyoss.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: ChangRui
 * @Date: 2020/4/18
 * @Description:
 */
public interface VodService {
    String uploadVideo(MultipartFile file) throws IOException;

    void deleteVideo(String id) throws ClientException;

    String getPlayAuth(String id);
}
