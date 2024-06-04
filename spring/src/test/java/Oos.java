import com.aliyun.oss.OSS;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.renjia.blog.config.OSSCloudConfig;
import com.renjia.blog.pojo.BlogUser;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static com.aliyun.oss.event.ProgressEventType.TRANSFER_COMPLETED_EVENT;


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
