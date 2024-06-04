package com.renjia.blog.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class OSSCloudConfig {

    public static String ENDPOINT; //外网访问
    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;
    public static String BUCKETNAME;
    public String objectPrefixName = "static/images/"; //基础文件夹
    public String filePathPrefix = "https://" + BUCKETNAME + "." + ENDPOINT + "/" + objectPrefixName; //带https的完整访问前缀

    static {
        InputStream resourceAsStream =
                OSSCloudConfig.class.getClassLoader().getResourceAsStream("config/oss.config.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            ENDPOINT = properties.getProperty("aliyun.endpoint");
            ACCESSKEYID = properties.getProperty("aliyun.accessKeyId");
            ACCESSKEYSECRET = properties.getProperty("aliyun.accessKeySecret");
            BUCKETNAME = properties.getProperty("aliyun.bucketName");
        } catch (IOException e) {
            log.error("没有找到oss的配置文件。。");
        }

    }

    public String getObjectPrefixName() {
        return objectPrefixName;
    }

    public void setObjectPrefixName(String objectPrefixName) {
        this.objectPrefixName = objectPrefixName;
    }

    public String getFilePathPrefix() {
        return filePathPrefix;
    }

    public void setFilePathPrefix(String filePathPrefix) {
        this.filePathPrefix = filePathPrefix;
    }
}
