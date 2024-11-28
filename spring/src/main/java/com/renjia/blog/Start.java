package com.renjia.blog;

import com.renjia.blog.netty.AdvanceBehavior;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ServletComponentScan
@EnableCaching
@EnableAspectJAutoProxy
@MapperScan("com.renjia.blog.mapper")
@PropertySource({"classpath:config/db.properties","classpath:config/core.properties"})
@Import(AdvanceBehavior.class)
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(com.renjia.blog.Start.class, args);
    }

}
