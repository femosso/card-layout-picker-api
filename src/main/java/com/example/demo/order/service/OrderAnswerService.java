package com.example.demo.order.service;

import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.persistence.entity.OrderAnswer;

import java.util.List;

public interface OrderAnswerService {

    OrderAnswer save(Order order, OrderAnswer orderAnswer);

    List<OrderAnswer> saveAll(Order order, List<OrderAnswer> orderAnswerList);

    List<OrderAnswer> listByOrder(Order order);

    List<OrderAnswer> deleteByOrder(Order order);

}
