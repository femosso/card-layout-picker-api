package com.example.demo.order.service.impl;

import com.example.demo.auth.service.AuthService;
import com.example.demo.order.persistence.entity.OrderStatus;
import com.example.demo.order.persistence.repository.OrderStatusRepository;
import com.example.demo.order.service.OrderStatusService;
import com.example.demo.user.persistence.entity.Role;
import com.example.demo.user.persistence.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private AuthService authService;

    @Override
    public List<OrderStatus> list() {
        User currentUser = authService.currentUser();
        if (Role.ROLE_ADMIN.equals(currentUser.getRole().getId())) {
            return orderStatusRepository.findAllById(
                    Arrays.asList(
                            OrderStatus.CREATED,
                            OrderStatus.IN_PRODUCTION,
                            OrderStatus.FINISHED
                    )
            );
        } else if (Role.ROLE_CLIENT.equals(currentUser.getRole().getId())) {
            return orderStatusRepository.findAll();
        }
        return new ArrayList<>();
    }
}