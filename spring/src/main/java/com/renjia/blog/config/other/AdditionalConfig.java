package com.renjia.blog.config.other;

import com.renjia.blog.config.OSSCloudConfig;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@Getter
@Slf4j
public class AdditionalConfig {
   public static String avatar;

    static {
        InputStream resourceAsStream =
                OSSCloudConfig.class.getClassLoader().getResourceAsStream("config/additional.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            avatar = properties.getProperty("default.avatar");
        } catch (IOException e) {
            log.error("没有找到默认额外的配置文件。。");
        }

    }
}
