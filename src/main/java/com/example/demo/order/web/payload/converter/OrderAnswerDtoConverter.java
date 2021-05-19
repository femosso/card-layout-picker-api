package com.example.demo.order.web.payload.converter;

import com.example.demo.card.web.payload.converter.CardFieldDtoConverter;
import com.example.demo.order.persistence.entity.OrderAnswer;
import com.example.demo.order.web.payload.OrderAnswerInDto;
import com.example.demo.order.web.payload.OrderAnswerOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderAnswerDtoConverter {

    @Autowired
    private CardFieldDtoConverter cardFieldDtoConverter;

    public OrderAnswer toEntity(OrderAnswerInDto dto) {
        if (dto == null) {
            return null;
        }

        OrderAnswer entity = new OrderAnswer();
        entity.setId(dto.getId());
        entity.setAnswer(dto.getAnswer());
        entity.setCardField(cardFieldDtoConverter.toEntity(dto.getCardField()));
        return entity;
    }

    public OrderAnswerOutDto toDto(OrderAnswer entity) {
        if (entity == null) {
            return null;
        }

        OrderAnswerOutDto dto = new OrderAnswerOutDto();
        dto.setId(entity.getId());
        dto.setAnswer(entity.getAnswer());
        dto.setCardField(cardFieldDtoConverter.toDto(entity.getCardField()));
        return dto;
    }
}