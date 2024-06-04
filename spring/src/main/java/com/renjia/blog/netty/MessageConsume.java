package com.renjia.blog.netty;

import com.renjia.blog.mq.MQType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageConsume implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum MessageType {
        COMMENT(MQType.comment),
        LIKE(MQType.like),
        ATTENTION(MQType.followWithInterest),
        FAVORITE(MQType.collect),
        CHATTINGRECORDS(MQType.privateLetter),
        OTHER(MQType.other);
        public final String type;

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
