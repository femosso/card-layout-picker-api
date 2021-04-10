package com.example.demo.auth.web.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDto {
    private String email;
    private String locale;
}
