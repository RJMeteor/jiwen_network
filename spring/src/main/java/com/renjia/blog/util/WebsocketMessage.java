package com.renjia.blog.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class WebsocketMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum MessageType {
        COMMENT("comment"), //评论
        LIKE("like"), //点赞
        ATTENTION("followWithInterest"), //关注
        FAVORITE("collect"), //收藏
        CHATTINGRECORDS("privateLetter;chat"), //聊天
        OTHER("other"); //其它
        private final String type;

        MessageType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private String type;
    private String message;

}
