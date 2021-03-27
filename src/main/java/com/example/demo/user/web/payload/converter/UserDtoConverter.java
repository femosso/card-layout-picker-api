package com.example.demo.user.web.payload.converter;

import com.example.demo.auth.web.payload.request.RegisterDto;
import com.example.demo.user.persistence.entity.User;
import com.example.demo.user.web.payload.UserOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleDtoConverter roleDtoConverter;

    public User toEntity(RegisterDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword() != null ? encoder.encode(dto.getPassword()) : null);
        user.setRole(roleDtoConverter.toEntity(dto.getRole()));
        return user;
    }

    public UserOutDto toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserOutDto dto = new UserOutDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
