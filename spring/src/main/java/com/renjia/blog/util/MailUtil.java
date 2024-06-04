package com.renjia.blog.util;

import com.renjia.blog.config.other.MailConfig;
import com.renjia.blog.util.exceptions.OtherException;
import com.renjia.blog.util.other.MailMessage;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MailUtil {
    private static final String MAIL_SPLIT = ";";

    private static JavaMailSender mailSender = BeanUtil.getBean(JavaMailSender.class);

    private static MailConfig mailConfig = BeanUtil.getBean(MailConfig.class);

    private static FreeMarkerConfig freeMarkerConfig = BeanUtil.getBean(FreeMarkerConfig.class);

    /**
     * 发送简单邮件信息
     * 使用请配置邮箱信息 参考
     * mail:
     * host: "smtp.163.com"
     * username: "XXXXXX@163.com"
     * password: "XXXXXX"  非邮箱登陆秘密；而是成功开启IMAP/SMTP服务，第三方客户端登录时的授权密码
     * default-encoding: UTF-8
     *
     * @param content 邮件内容
     * @param subject 邮件标题
     * @param toEmail 收件人邮箱
     * @return boolean 发送是否成功
     */
    public static boolean sendSimpleMail(String content, String toEmail, String subject) {
        //邮件信息封装
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfig.getUsername());
        message.setTo(toEmail.split(MAIL_SPLIT));
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error(String.format("邮件发送失败；【mailAddr：%s, cc:%s, subject:%s, content:%s 】", subject, content), e);
            return false;
        }
        return true;
    }


    /**
     * 模板邮件发送
     *
     * @param params       发送邮件的主题对象 <desc>封装数据类，字段参看email模板</desc>
     * @param title        邮件标题
     * @param toEmail      收件人邮箱
     * @param templateName 模板名称
     */
    public static void sendMessageMail(MailMessage params, String title, String toEmail, String templateName) throws OtherException{
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailConfig.getUsername());
            helper.setTo(toEmail.split(MAIL_SPLIT));
            //邮件标题
            helper.setSubject(String.format("【%s-%s %s】", title, LocalDate.now(), LocalTime.now().withNano(0)));
            Map<String, Object> model = new HashMap<>();
            model.put("params", params);
            try {
                Template template = freeMarkerConfig.getConfiguration().getTemplate(templateName);
                try {
                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    helper.setText(text, true);
                    mailSender.send(mimeMessage);
                } catch (TemplateException e) {
                    log.error(String.format("邮件发送失败；【params：%s, title:%s, templateName:%s 】", params, title, templateName), e);
                    throw new OtherException("验证码发送失败！");
                }
            } catch (IOException e) {
                log.error(String.format("邮件发送失败；【params：%s, title:%s, templateName:%s 】", params, title, templateName), e);
                throw new OtherException("验证码发送失败！");
            }
        } catch (MessagingException e) {
            log.error(String.format("邮件发送失败；【params：%s, title:%s, templateName:%s 】", params, title, templateName), e);
            throw new OtherException("验证码发送失败！");
        }
    }

}
