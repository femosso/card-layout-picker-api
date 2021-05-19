package com.example.demo.order.web.payload.converter;

import com.example.demo.card.web.payload.converter.CardFieldDtoConverter;
import com.example.demo.order.persistence.entity.OrderStatus;
import com.example.demo.order.web.payload.OrderStatusOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusDtoConverter {

    @Autowired
    private CardFieldDtoConverter cardFieldDtoConverter;

    public OrderStatusOutDto toDto(OrderStatus entity) {
        if (entity == null) {
            return null;
        }

        OrderStatusOutDto dto = new OrderStatusOutDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName().name());
        return dto;
    }
}