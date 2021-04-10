package com.example.demo.common.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Mail {

    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contentType;
    private Map<String, Object> model;

    public Mail() {
        contentType = "text/plain";
    }
}