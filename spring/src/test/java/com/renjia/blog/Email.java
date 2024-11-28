package com.renjia.blog;

import com.renjia.blog.util.other.MailMessage;
import com.renjia.blog.util.MailUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Email {

    @Resource
    private JavaMailSender javaMailSender;

    public void senderMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人 你的邮箱
        message.setFrom("xxxxx@qq.com");
        // 接收人 接收者邮箱
        message.setTo(new String[]{"xxx@qq.com"});

        //邮件标题
        message.setSubject("hello");

        //邮件内容
        message.setText("world");

        javaMailSender.send(message);

    }

    public void mail() {
        MailMessage message = new MailMessage();

        message.setCode("XXXXXXX");
        message.setContent("服务对数据库读写压力大");
        message.setTime("5");

        MailUtil.sendMessageMail(message, "测试消息通知", "renjia_new@qq.com", "emailTest.ft");
    }

}
