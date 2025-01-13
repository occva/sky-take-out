package com.sky.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.sky.properties.TencentCosProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

/**
 * 腾讯云 COS 工具类，用于文件上传等操作
 */
@Slf4j
@Data
@AllArgsConstructor
public class CosUtil {

    private TencentCosProperties cosProperties;

    /**
     * 文件上传到腾讯云 COS
     *
     * @param bytes      文件字节数组
     * @param objectName 文件名（带路径）
     * @return 上传后的文件访问 URL
     */
    public String upload(byte[] bytes, String objectName) {
        // 创建 COS 客户端
        BasicCOSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(cosProperties.getRegion()));
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 构建上传请求
            String bucketName = cosProperties.getTestBucket() + "-" + cosProperties.getAppid();
            String cosPath = cosProperties.getCosPath() != null ? cosProperties.getCosPath() + "/" : "";
            String fullPath = cosPath + objectName;

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    fullPath,
                    new ByteArrayInputStream(bytes),
                    null
            );

            // 执行上传
            cosClient.putObject(putObjectRequest);

            // 构建文件访问 URL
            String fileUrl = String.format("https://%s.cos.%s.myqcloud.com/%s",
                    bucketName,
                    cosProperties.getRegion(),
                    fullPath);

            log.info("文件上传成功，访问 URL: {}", fileUrl);
            return fileUrl;

        } catch (CosClientException e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new RuntimeException("文件上传失败", e);

        } finally {
            // 关闭客户端
            if (cosClient != null) {
                cosClient.shutdown();
            }
        }
    }
}
