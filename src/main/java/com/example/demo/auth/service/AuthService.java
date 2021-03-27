package com.example.demo.auth.service;

import com.example.demo.auth.web.payload.response.LoginResponseDto;

public interface AuthService {

    LoginResponseDto authenticate(String email, String password);

}
