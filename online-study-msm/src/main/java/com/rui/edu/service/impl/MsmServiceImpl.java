package com.rui.edu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.rui.edu.service.MsmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ChangRui
 * @Date: 2020/4/21
 * @Description:
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Value("${aliyun.oss.file.keyid}")
    private String KEY_ID;

    @Value("${aliyun.oss.file.keysecret}")
    private String KEY_SERET;

    @Override
    public boolean send(String code, String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", KEY_ID, KEY_SERET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 设置发送参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "奥利给在线学习平台网站");
        request.putQueryParameter("TemplateCode", "SMS_188630647");
        Map map = new HashMap();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        // 发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
