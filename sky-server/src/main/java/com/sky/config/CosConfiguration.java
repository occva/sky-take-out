package com.sky.config;

import com.sky.properties.TencentCosProperties;
import com.sky.utils.CosUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CosConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CosUtil cosUtil(TencentCosProperties cosProperties) {
        log.info("开始创建腾讯云COS文件上传工具类对象: {}", cosProperties);
        return new CosUtil(cosProperties);
    }
}
