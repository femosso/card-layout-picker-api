package com.example.demo.order.web.payload.converter;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.web.payload.converter.CardDtoConverter;
import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.persistence.entity.OrderAnswer;
import com.example.demo.order.persistence.entity.OrderStatus;
import com.example.demo.order.web.payload.OrderInDto;
import com.example.demo.order.web.payload.OrderOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoConverter {

    @Autowired
    private CardDtoConverter cardDtoConverter;
    @Autowired
    private OrderAnswerDtoConverter orderAnswerDtoConverter;

    public Order toEntity(OrderInDto dto) {
        if (dto == null) {
            return null;
        }

        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPersonId(dto.getPersonId());
        entity.setEmployeeId(dto.getEmployeeId());
        if (dto.getStatusId() != null) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(dto.getStatusId());
            entity.setStatus(orderStatus);
        }
        if (dto.getCard() != null) {
            Card card = new Card();
            card.setId(dto.getCard().getId());
            entity.setCard(card);
        }
        return entity;
    }

    public OrderOutDto toDto(Order entity, List<OrderAnswer> orderAnswerList) {
        if (entity == null) {
            return null;
        }

        OrderOutDto dto = new OrderOutDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPersonId(entity.getPersonId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setStatusId(entity.getStatus().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCard(cardDtoConverter.toMinimalDto(entity.getCard()));
        dto.setOrderAnswers(orderAnswerList.stream().map(orderAnswerDtoConverter::toDto).collect(Collectors.toList()));
        return dto;
    }
}