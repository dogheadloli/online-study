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
    public String uploadVideo(MultipartFile file) throws IOException;

    public void deleteVideo(String id) throws ClientException;
}
