package com.example.demo.auth.service.impl;

import com.example.demo.auth.domain.UserDetailsImpl;
import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.web.payload.response.LoginResponseDto;
import com.example.demo.auth.web.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public LoginResponseDto authenticate(String email, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return new LoginResponseDto(
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getUsername(),
                userDetails.getRole(),
                jwtUtils.generateJwtToken(userDetails)
        );
    }
}
