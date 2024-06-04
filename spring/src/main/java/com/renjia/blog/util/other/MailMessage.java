package com.renjia.blog.util.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailMessage implements Serializable {
    private String content;
    private String code;
    private String time;
}
