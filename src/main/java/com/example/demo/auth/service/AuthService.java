package com.example.demo.auth.service;

import com.example.demo.auth.web.payload.response.LoginResponseDto;
import com.example.demo.user.persistence.entity.User;

public interface AuthService {

    LoginResponseDto authenticate(String email, String password);

    User currentUser();

}
