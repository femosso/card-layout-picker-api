package com.example.demo.auth.web.controller;

import com.example.demo.auth.service.AuthService;
import com.example.demo.auth.web.payload.request.ForgotPasswordDto;
import com.example.demo.auth.web.payload.request.LoginDto;
import com.example.demo.auth.web.payload.request.RegisterDto;
import com.example.demo.auth.web.payload.request.UpdatePasswordDto;
import com.example.demo.auth.web.payload.response.LoginResponseDto;
import com.example.demo.common.service.MessageService;
import com.example.demo.common.web.payload.MessageResponse;
import com.example.demo.user.domain.RoleType;
import com.example.demo.user.persistence.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.web.payload.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserDtoConverter userDtoConverter;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        LoginResponseDto response = authService.authenticate(loginDto.getEmail(), loginDto.getPassword());
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        registerDto.setRole(RoleType.ROLE_ADMIN.name());
        User user = userService.save(userDtoConverter.toEntity(registerDto));
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!", null, 3));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) {
        return new ResponseEntity<>("",
                messageService.sendForgotPasswordMessage(forgotPasswordDto.getEmail(), forgotPasswordDto.getLocale()) ?
                        HttpStatus.OK :
                        HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto,
                                                 @RequestParam(name = "validate", defaultValue = "false") boolean validate) {
        return validate ?
                new ResponseEntity<>("",
                        userService.isChangePasswordTokenValid(updatePasswordDto.getToken()) ?
                                HttpStatus.OK :
                                HttpStatus.FORBIDDEN
                ) :
                new ResponseEntity<>("",
                        userService.changePasswordByToken(updatePasswordDto.getToken(), updatePasswordDto.getPassword()) ?
                                HttpStatus.OK :
                                HttpStatus.FORBIDDEN
                );
    }
}

