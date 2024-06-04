package com.renjia.blog.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.renjia.blog.config.OSSCloudConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class OSSCloudClient {


    private static class SingletonContainer {
        private static OSSCloudClient instance = new OSSCloudClient();
    }

    public static OSSCloudClient getInstance() {
        return SingletonContainer.instance;
    }

    private OSS createOSS() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTPS);

        // 创建OSSClient实例。
        return new OSSClientBuilder().build(OSSCloudConfig.ENDPOINT, OSSCloudConfig.ACCESSKEYID, OSSCloudConfig.ACCESSKEYSECRET);
    }

    private Map<String, String> getObjectName(String fileName, String userId) {
        Map<String, String> nameMap = new HashMap<>();
        OSSCloudConfig config = new OSSCloudConfig();
        String name = userId + "/" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
        nameMap.put("objectName", config.getObjectPrefixName() + name);
        nameMap.put("filePath", config.getFilePathPrefix() + name);
        return nameMap;
    }


    private String getObjectName(String filePath) {
        OSSCloudConfig config = new OSSCloudConfig();
        return config.getObjectPrefixName() + filePath.replace(config.getFilePathPrefix(), "");
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名称
     * @param userId   用户id
     * @param bytes    byte数组
     * @throws Exception
     */
    public String uploadFile(String fileName, String userId, byte[] bytes) throws Exception {
        Map<String, String> nameMap = this.getObjectName(fileName, userId);
        String objectName = nameMap.get("objectName");
        OSS ossClient = createOSS();
        try {
            ossClient.putObject(OSSCloudConfig.BUCKETNAME, objectName, new ByteArrayInputStream(bytes));
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return nameMap.get("filePath");
    }

    /**
     * 上传文件
     *
     * @param fileName  文件名称
     * @param userId    用户id
     * @param localPath 本地文件路径
     * @throws Exception
     */
    public String uploadFile(String fileName, String userId, String localPath) throws Exception {
        Map<String, String> nameMap = this.getObjectName(fileName, userId);
        String objectName = nameMap.get("objectName");
        OSS ossClient = createOSS();
        try {
            ossClient.putObject(OSSCloudConfig.BUCKETNAME, objectName, new FileInputStream(new File(localPath)));
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return nameMap.get("filePath");
    }
    private Map<String, String> getName(String fileName) {
        Map<String, String> nameMap = new HashMap<>();
        OSSCloudConfig config = new OSSCloudConfig();
        String name =  UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
        nameMap.put("objectName", config.getObjectPrefixName() + name);
        nameMap.put("filePath", config.getFilePathPrefix() + name);
        return nameMap;
    }
    /**
     * 上传文件
     *
     * @param fileName 文件名称
     * @param userId   用户id
     * @param is       文件输入流(fileInputStream或InputStream)
     * @throws Exception
     */
    public String uploadFile(String fileName, String userId, InputStream is) throws Exception {
        Map<String, String> nameMap = this.getObjectName(fileName, userId);
        String objectName = nameMap.get("objectName");
        OSS ossClient = createOSS();
        try {
            ossClient.putObject(OSSCloudConfig.BUCKETNAME, objectName, is);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return nameMap.get("filePath");
    }

    /**
     * 上传文件
     *
     * @param fileName 文件名称
     * @param is       文件输入流(fileInputStream或InputStream)
     * @throws Exception
     */
    public String uploadFile(String fileName, InputStream is) throws Exception {
        Map<String, String> nameMap = this.getName(fileName);
        String objectName = nameMap.get("objectName");
        OSS ossClient = createOSS();
        try {
            ossClient.putObject(OSSCloudConfig.BUCKETNAME, objectName, is);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return nameMap.get("filePath");
    }

    /**
     * 文件是否存在
     *
     * @param filePath 文件完整访问路径(带https)
     * @return
     */
    public Boolean fileExist(String filePath) {
        String objectName = this.getObjectName(filePath);
        OSS ossClient = createOSS();
        try {
            return ossClient.doesObjectExist(OSSCloudConfig.BUCKETNAME, objectName);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 列举文件
     *
     * @param dirPath 想要列举的文件夹 比如1/20220415
     * @return
     */
    public List<String> listFiles(String dirPath) {
        OSSCloudConfig config = new OSSCloudConfig();
        String objectDir = config.getObjectPrefixName() + dirPath;
//      String objectName = this.getObjectName(filePath);
        OSS ossClient = createOSS();
        try {
            ObjectListing objectListing = ossClient.listObjects(OSSCloudConfig.BUCKETNAME, objectDir);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            List<String> list = new ArrayList<>();
            for (OSSObjectSummary s : sums) {
                list.add(config.getFilePathPrefix() + (s.getKey().replace(config.getObjectPrefixName(), "")));
            }
            return list;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @param filePath 删除文件或目录。如果要删除目录，目录必须为空。 (带域名访问路径的)
     * @throws Exception
     */
    public void deleteFile(String filePath) throws Exception {
        String objectName = this.getObjectName(filePath);
        OSS ossClient = createOSS();
        try {
            ossClient.deleteObject(OSSCloudConfig.BUCKETNAME, objectName);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    static void test(String[] args) throws Exception {
        OSSCloudClient client = OSSCloudClient.getInstance();
        String filePath = client.uploadFile("读书记录封面.png", "1", "D:\\Pictures\\bookrecording\\读书记录封面.png");
        System.out.println(filePath);
        System.out.println(client.fileExist(filePath));
        List<String> list = client.listFiles("1/20220416/");
        for (String s : list) {
            System.out.println(s);
            client.deleteFile(s);
        }
        client.deleteFile("https://zhaohy-bucket.oss-cn-guangzhou.aliyuncs.com/static/images/1/20220415");
    }
}
