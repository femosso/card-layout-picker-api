package com.example.demo.user.web.controller;

import com.example.demo.auth.web.payload.request.RegisterDto;
import com.example.demo.common.web.payload.MessageResponse;
import com.example.demo.user.domain.RoleType;
import com.example.demo.user.persistence.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.web.payload.UserOutDto;
import com.example.demo.user.web.payload.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoConverter userDtoConverter;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')") // TODO - check why with only ADMIN this also works
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody RegisterDto registerDto) {
        registerDto.setRole(RoleType.ROLE_CLIENT.name());
        User user = userService.save(userDtoConverter.toEntity(registerDto));
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Client registered successfully!", null, 3));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") UUID id,
                                                  @Valid @RequestBody RegisterDto registerDto) {
        User user = userDtoConverter.toEntity(registerDto);
        user.setId(id);
        user = userService.save(user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Client registered successfully!", null, 3));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserOutDto> get(@PathVariable("id") UUID id) {
        return userService.get(id)
                .map(user -> ResponseEntity.ok(userDtoConverter.toDto(user)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserOutDto>> list() {
        return ResponseEntity.ok(userService.list()
                .stream()
                .map(userDtoConverter::toDto)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id) {
        Optional<User> user = userService.delete(id);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Client deleted successfully!", null, 3));
    }
}