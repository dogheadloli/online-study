package com.rui.onlinestudyoss.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;

/**
 * @Author: ChangRui
 * @Date: 2020/4/9
 * @Description:
 */
public class OSSTest {
    @Test
    public void testOSS() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FpKYxafxU2u8Fn3fy1W";
        String accessKeySecret = "YQE8wq7swsg5TkNuZUIbDc4iHhYFLS";
        String bucketName = "onlline-study-file-1";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
