package com.renjia.blog;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Oos {
//    @Resource
//    private OSS ossClient;
//    @Resource
//    private OSSCloudConfig ossConfig;


//    public void uploadImg(List<MultipartFile> file, Integer userId) throws IOException {
//        Iterator<MultipartFile> iterator = file.iterator();
//        ArrayList<String> strings = new ArrayList<>();
//        CountDownLatch countDownLatch = new CountDownLatch(file.size());
//        while (iterator.hasNext()) {
//            MultipartFile next = iterator.next();
//            InputStream inputStream = next.getInputStream();
//            String originalFilename = next.getOriginalFilename();
//            String ext = originalFilename.substring(originalFilename.indexOf("."));
//
//            String filename = String.valueOf(UUID.randomUUID());
//            String uploadFileUrl = new StringBuffer(filename).append(ext).toString();
//            PutObjectRequest putObjectRequest = new PutObjectRequest(
//                    ossConfig.getBucketName(),
//                    uploadFileUrl,
//                    inputStream);
//            ProgressListener progressListener = new ProgressListener() {
//                // 处理上传进度事件
//                @Override
//                public void progressChanged(ProgressEvent progressEvent) {
//                    if (progressEvent.getEventType() == TRANSFER_COMPLETED_EVENT) {
//                        // 判断上传结果状态码，200表示上传成功
//                        countDownLatch.countDown();
//                        strings.add(OSSCloudConfig.bucket + uploadFileUrl);
//                        BlogUser blogUser = new BlogUser();
//                        blogUser.setUserImage(OSSCloudConfig.bucket + uploadFileUrl);
//                        LambdaUpdateWrapper<BlogUser> blogUserLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//                        blogUserLambdaUpdateWrapper.eq(BlogUser::getId, userId)
//                                .set(BlogUser::getUserImage, OSSCloudConfig.bucket + uploadFileUrl);
//                    }
//                }
//            };
//            putObjectRequest.setProgressListener(progressListener);
//            // 上传文件
//            ossClient.putObject(putObjectRequest);
//        }
//    }
}
