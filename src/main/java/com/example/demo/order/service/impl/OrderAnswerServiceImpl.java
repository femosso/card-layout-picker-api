package com.example.demo.order.service.impl;

import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.persistence.entity.OrderAnswer;
import com.example.demo.order.persistence.repository.OrderAnswerRepository;
import com.example.demo.order.service.OrderAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderAnswerServiceImpl implements OrderAnswerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderAnswerRepository orderAnswerRepository;

    @Override
    @Transactional
    public OrderAnswer save(Order order, OrderAnswer orderAnswer) {
        orderAnswer.setOrder(order);
        return orderAnswerRepository.save(orderAnswer);
    }

    @Override
    @Transactional
    public List<OrderAnswer> saveAll(Order order, List<OrderAnswer> orderAnswerList) {
        orderAnswerList.forEach(orderAnswer -> orderAnswer.setOrder(order));
        return orderAnswerRepository.saveAll(orderAnswerList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderAnswer> listByOrder(Order order) {
        return orderAnswerRepository.findByOrderId(order.getId());
    }

    @Override
    @Transactional
    public List<OrderAnswer> deleteByOrder(Order order) {
        return orderAnswerRepository.deleteByOrderId(order.getId());
    }
}