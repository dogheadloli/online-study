package com.rui.onlinestudyoss.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: ChangRui
 * @Date: 2020/4/9
 * @Description:
 */
@Component
public class ConstantPropertiesUtils {

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

}
