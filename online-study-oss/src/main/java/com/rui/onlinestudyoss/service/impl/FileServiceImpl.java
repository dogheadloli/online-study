package com.rui.onlinestudyoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.rui.common.Exception.OnlineStudyException;
import com.rui.common.constants.ResultCodeEnum;
import com.rui.onlinestudyoss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: ChangRui
 * @Date: 2020/4/10
 * @Description:
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${aliyun.oss.file.endpoint}")
    private String END_POINT;

    @Value("${aliyun.oss.file.keyid}")
    private String KEY_ID;

    @Value("${aliyun.oss.file.keysecret}")
    private String KEY_SERET;

    @Value("${aliyun.oss.file.bucketname}")
    private String BUCKET_NAME;

    @Value("${aliyun.oss.file.filehost}")
    private String FILE_HOST;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(END_POINT, KEY_ID, KEY_SERET);
            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
                ossClient.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
            }
            InputStream inputStream = file.getInputStream();

            // 上传位置
            String originalFileName = file.getOriginalFilename();
            String fileType = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString();
            String date = new DateTime().toString("yyyy/MM/dd");

            String path = FILE_HOST + "/" + date + "/" + newFilename + fileType;

            ossClient.putObject(BUCKET_NAME, path, inputStream);

            ossClient.shutdown();

            String url = "https://" + BUCKET_NAME + "." + END_POINT + "/" + path;

            return url;

        } catch (IOException e) {
            throw new OnlineStudyException(ResultCodeEnum.FILE_UPLOAD_ERROR.getCode(),
                    ResultCodeEnum.FILE_UPLOAD_ERROR.getMessage());
        }
    }
}
