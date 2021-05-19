package com.example.demo.order.service;

import com.example.demo.common.web.payload.PagingFilter;
import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.persistence.entity.OrderAnswer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Order save(Order order, List<OrderAnswer> orderAnswers);

    Optional<Order> get(UUID id);

    Page<Order> list(PagingFilter pagingFilter);

    Optional<Order> delete(UUID id);

    boolean validatePersonId(UUID id, String personId);

    void updateStatuses(List<Order> orderList);
}
