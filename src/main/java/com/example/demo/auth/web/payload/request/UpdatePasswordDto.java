package com.example.demo.auth.web.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePasswordDto {
    private UUID token;
    private String password;
}
