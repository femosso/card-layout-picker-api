package com.example.demo.user.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserOutDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
