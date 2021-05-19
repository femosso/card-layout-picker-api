package com.example.demo.order.persistence.repository;

import com.example.demo.order.persistence.entity.OrderAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderAnswerRepository extends JpaRepository<OrderAnswer, UUID> {

    List<OrderAnswer> findByOrderId(UUID orderId);

    List<OrderAnswer> deleteByOrderId(UUID orderId);

}