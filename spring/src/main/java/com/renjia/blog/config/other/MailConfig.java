package com.renjia.blog.config.other;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("spring.mail")
public class MailConfig {
    /**
     * 发信箱邮箱账号
     */
    private String username;

}
