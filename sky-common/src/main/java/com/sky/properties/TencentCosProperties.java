package com.sky.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.tencent-cos")
@Data
public class TencentCosProperties {

    private String appid;         // 腾讯云APPID
    private String secretId;      // 腾讯云SecretId
    private String secretKey;     // 腾讯云SecretKey
    private String testBucket;    // 存储桶名称
    private String cosPath;       // COS存储路径
    private String region;        // 地域
}


