package com.example.demo.user.service.impl;

import com.example.demo.user.domain.RoleType;
import com.example.demo.user.persistence.helper.RoleLookupHelper;
import com.example.demo.user.persistence.repository.RoleRepository;
import com.example.demo.user.persistence.entity.User;
import com.example.demo.user.persistence.repository.UserRepository;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        UUID userId = user.getId();
        if (userId == null) {
            // TODO - add creation time
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

    private void updateFields(User from, User to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());

        // TODO - set updated time
    }
}