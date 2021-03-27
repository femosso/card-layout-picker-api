package com.example.demo.user.persistence.repository;

import com.example.demo.user.persistence.entity.Role;
import com.example.demo.user.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findByRoleId(UUID roleId);

    @Modifying
    void deleteById(UUID id);
}