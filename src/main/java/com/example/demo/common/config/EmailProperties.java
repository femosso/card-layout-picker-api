package com.example.demo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("demo.email")
@Getter
@Setter
public class EmailProperties {

    private String host;
    private String port;
    private String username;
    private String password;

}