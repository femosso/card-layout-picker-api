package com.example.demo.card.web.payload.converter;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.web.payload.CardInDto;
import com.example.demo.card.web.payload.CardOutDto;
import com.example.demo.card.web.payload.MinimalCardOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardDtoConverter {

    @Autowired
    private CardFieldDtoConverter cardFieldDtoConverter;

    public Card toEntity(CardInDto dto) {
        if (dto == null) {
            return null;
        }

        Card entity = new Card();
        entity.setId(dto.getId());
        return entity;
    }

    public CardOutDto toDto(Card entity, List<CardField> cardFieldList) {
        if (entity == null) {
            return null;
        }

        CardOutDto dto = new CardOutDto();
        fillMinimalDto(entity, dto);
        dto.setFields(cardFieldList.stream().map(cardFieldDtoConverter::toDto).collect(Collectors.toList()));
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public MinimalCardOutDto toMinimalDto(Card entity) {
        if (entity == null) {
            return null;
        }
        MinimalCardOutDto minimalCardOutDto = new MinimalCardOutDto();
        fillMinimalDto(entity, minimalCardOutDto);
        return minimalCardOutDto;
    }

    private void fillMinimalDto(Card entity, MinimalCardOutDto dto) {
        dto.setId(entity.getId());
        dto.setImagePath(entity.getFile().getUrl());
    }
}