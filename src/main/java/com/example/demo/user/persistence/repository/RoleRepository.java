package com.example.demo.user.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.demo.user.domain.RoleType;
import com.example.demo.user.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

}