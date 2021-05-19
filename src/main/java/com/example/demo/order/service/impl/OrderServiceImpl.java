package com.example.demo.order.service.impl;

import com.example.demo.auth.service.AuthService;
import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.common.web.payload.PagingFilter;
import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.persistence.entity.OrderAnswer;
import com.example.demo.order.persistence.entity.OrderStatus;
import com.example.demo.order.persistence.repository.OrderRepository;
import com.example.demo.order.service.OrderAnswerService;
import com.example.demo.order.service.OrderService;
import com.example.demo.user.persistence.entity.Role;
import com.example.demo.user.persistence.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderAnswerService orderAnswerService;
    @Autowired
    private AuthService authService;

    @Override
    @Transactional
    public Order save(Order order, List<OrderAnswer> orderAnswers) {
        UUID orderId = order.getId();
        if (orderId == null) {
            changeOrderStatus(order, OrderStatus.WAITING_EMPLOYEE);
            order.setCreatedAt(Instant.now());
            order.setUpdatedAt(order.getCreatedAt());
            order.setCreatedBy(authService.currentUser());
            return orderRepository.save(order);
        }
        Order entity = orderRepository.findById(orderId).orElse(null);
        if (entity == null) return null;
        updateFields(order, entity, orderAnswers);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> get(UUID id) {
        User user = authService.currentUser();
        if (user == null) {
            return orderRepository.findByIdAndStatusId(id, OrderStatus.WAITING_EMPLOYEE);
        } else if (user.getRole().getId().equals(Role.ROLE_CLIENT)) {
            return orderRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> list(PagingFilter pagingFilter) {
        return orderRepository.findBySearch(
                readStatusFilterBasedOnCurrentUser(pagingFilter),
                pagingFilter.getSearchMap().get("name"),
                pagingFilter.getFromDate(),
                pagingFilter.getToDate(),
                pagingFilter.getPaging()
        );
    }

    private Collection<UUID> readStatusFilterBasedOnCurrentUser(PagingFilter pagingFilter) {
        String statusFilter = pagingFilter.getSearchMap().get("statusId");
        if (StringUtils.hasText(statusFilter)) {
            return Collections.singleton(UUID.fromString(statusFilter));
        } else if (Role.ROLE_ADMIN.equals(authService.currentUser().getRole().getId())) {
            return Arrays.asList(
                    OrderStatus.CREATED,
                    OrderStatus.IN_PRODUCTION,
                    OrderStatus.FINISHED
            );
        }
        return null;
    }

    @Override
    @Transactional
    public Optional<Order> delete(UUID id) {
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validatePersonId(UUID id, String personId) {
        return orderRepository.findById(id).map(value -> value.getPersonId().equals(personId)).orElse(false);
    }

    @Override
    @Transactional
    public void updateStatuses(List<Order> orderList) {
        orderRepository.findByIdIn(orderList
                .stream()
                .map(AbstractEntity::getId)
                .collect(Collectors.toList())
        ).forEach(order -> changeOrderStatus(order, order.getStatus().getName().next().id()));
    }

    private void updateFields(Order from, Order to, List<OrderAnswer> orderAnswers) {
        updateStatus(from, to, orderAnswers);
        to.setName(from.getName());
        to.setPersonId(from.getPersonId());
        to.setEmployeeId(from.getEmployeeId());
        to.setUpdatedAt(Instant.now());
    }

    private void updateStatus(Order from, Order to, List<OrderAnswer> orderAnswers) {
        if (OrderStatus.WAITING_EMPLOYEE.equals(to.getStatus().getId())) {
            orderAnswerService.deleteByOrder(from);
            orderAnswerService.saveAll(from, orderAnswers);
            changeOrderStatus(to, OrderStatus.VALIDATING_FIELDS);
        } else if (OrderStatus.VALIDATING_FIELDS.equals(to.getStatus().getId())) {
            if (isCreatedOrWaitingEmployeeStatus(from.getStatus().getId())) {
                changeOrderStatus(to, from.getStatus().getId());
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean isCreatedOrWaitingEmployeeStatus(UUID statusId) {
        return OrderStatus.CREATED.equals(statusId) || OrderStatus.WAITING_EMPLOYEE.equals(statusId);
    }

    private void changeOrderStatus(Order order, UUID status) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(status);
        order.setStatus(orderStatus);
    }
}