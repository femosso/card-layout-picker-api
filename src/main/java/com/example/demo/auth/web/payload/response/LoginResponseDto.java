package com.example.demo.auth.web.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class LoginResponseDto {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String role;
    private final String token;
}