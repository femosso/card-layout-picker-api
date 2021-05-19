package com.example.demo.user.service.impl;

import com.example.demo.user.persistence.entity.PasswordResetToken;
import com.example.demo.user.persistence.entity.User;
import com.example.demo.user.persistence.helper.RoleLookupHelper;
import com.example.demo.user.persistence.repository.PasswordResetTokenRepository;
import com.example.demo.user.persistence.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Value("${demo.password.resetTokenExpire}")
    private Long resetTokenExpire;

    @Override
    @Transactional
    public User save(User user) {
        UUID userId = user.getId();
        if (userId == null) {
            user.setCreatedAt(Instant.now());
            user.setUpdatedAt(user.getCreatedAt());
            return userRepository.save(user);
        }
        User entity = userRepository.findById(userId).orElse(null);
        if (entity == null) return null;
        updateFields(user, entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return userRepository.findByRoleId(RoleLookupHelper.ROLE_CLIENT);
    }

    @Override
    @Transactional
    public Optional<User> delete(UUID id) {
        Optional<User> user = get(id);
        userRepository.deleteById(id);
        return user;
    }

    @Override
    @Transactional
    public PasswordResetToken createPasswordResetToken(String email) {
        Optional<User> user = getByEmail(email);
        if (!user.isPresent()) return null;

        int tokensRemoved = passwordResetTokenRepository.deleteByUserId(user.get().getId());
        logger.debug("{} password reset token(s) removed for the user.", tokensRemoved);

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user.get());
        token.setToken(UUID.randomUUID());
        token.setExpireAt(Instant.now().plus(resetTokenExpire, ChronoUnit.HOURS));
        return passwordResetTokenRepository.save(token);
    }

    @Override
    public boolean isChangePasswordTokenValid(UUID tokenId) {
        Optional<PasswordResetToken> token = passwordResetTokenRepository.findByToken(tokenId);
        return token.isPresent() && Instant.now().isBefore(token.get().getExpireAt());
    }

    @Override
    @Transactional
    public boolean changePasswordByToken(UUID tokenId, String password) {
        Optional<PasswordResetToken> token = passwordResetTokenRepository.findByToken(tokenId);
        if (token.isPresent() && Instant.now().isBefore(token.get().getExpireAt())) {
            User user = token.get().getUser();
            user.setPassword(encoder.encode(password));
            passwordResetTokenRepository.delete(token.get());
            return true;
        }
        return false;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24) // once a day
    @Transactional
    public void performDbClearance() {
        Instant now = Instant.now();
        int tokensRemoved = passwordResetTokenRepository.deleteByExpireAtLessThanEqual(now);
        logger.debug("{} expired password reset token(s) removed.", tokensRemoved);
    }

    private void updateFields(User from, User to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setUpdatedAt(Instant.now());
    }
}