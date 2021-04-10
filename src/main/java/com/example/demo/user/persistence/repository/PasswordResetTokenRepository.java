package com.example.demo.user.persistence.repository;

import com.example.demo.user.persistence.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    int deleteByExpireAtLessThanEqual(Instant time);

}