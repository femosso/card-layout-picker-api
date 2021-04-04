package com.example.demo.user.web.payload.converter;

import com.example.demo.user.domain.RoleType;
import com.example.demo.user.persistence.entity.Role;
import com.example.demo.user.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter {

    @Autowired
    private RoleRepository roleRepository;

    public Role toEntity(String dto) {
        if (dto == null) {
            return null;
        }

        return roleRepository.findByName(RoleType.valueOf(dto)).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    public String toDto(Role role) {
        if (role == null) {
            return null;
        }

        return role.toString();
    }
}