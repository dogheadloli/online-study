package com.rui.onlinestudyoss.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.rui.common.Exception.OnlineStudyException;
import com.rui.onlinestudyoss.service.VodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ChangRui
 * @Date: 2020/4/18
 * @Description:
 */
@Slf4j
@Service
public class VodServiceImpl implements VodService {
    @Value("${aliyun.oss.file.keyid}")
    private String KEY_ID;

    @Value("${aliyun.oss.file.keysecret}")
    private String KEY_SERET;


    @Override
    public String uploadVideo(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));

        UploadStreamRequest request = new UploadStreamRequest(KEY_ID, KEY_SERET, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        }
        return videoId;

    }

    @Override
    public void deleteVideo(String id) throws ClientException {
        DefaultAcsClient client = null;
        client = initVodClient(KEY_ID, KEY_SERET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        client.getAcsResponse(request);
    }

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
