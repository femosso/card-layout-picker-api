package com.example.demo.user.service;

import com.example.demo.user.persistence.entity.PasswordResetToken;
import com.example.demo.user.persistence.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User save(User user);

    Optional<User> get(UUID id);

    Optional<User> getByEmail(String email);

    List<User> list();

    Optional<User> delete(UUID id);

    PasswordResetToken createPasswordResetToken(UUID userId);

    boolean isPasswordResetTokenValid(UUID tokenId);

    boolean changePasswordByToken(UUID tokenId, String password);

}
