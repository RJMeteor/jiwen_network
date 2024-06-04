package com.renjia.blog.netty;

import com.renjia.blog.mq.producer.MessageProcessingChain;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import java.io.Serializable;


@Data
public class AdvanceBehavior implements CommandLineRunner, Serializable {
    @Value("${netty.port}")
    Integer nettyPort;
    @Value("${netty.url}")
    String nettyUrl;
    @Value("${netty.host}")
    private String host= "localhost";
    @Override
    public void run(String... args) throws Exception {
        try {
            new MessageProcessingChain().initChai();
            System.out.println("nettyServer starting ...");
            new NettyServer(nettyPort).start(nettyUrl);
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }

}
