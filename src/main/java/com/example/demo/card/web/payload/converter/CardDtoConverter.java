package com.example.demo.card.web.payload.converter;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.web.payload.CardInDto;
import com.example.demo.card.web.payload.CardOutDto;
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
        return entity;
    }

    public CardOutDto toDto(Card entity, List<CardField> cardFieldList) {
        if (entity == null) {
            return null;
        }

        CardOutDto dto = new CardOutDto();
        dto.setId(entity.getId());
        dto.setImagePath(entity.getFile().getUrl());
        dto.setFields(cardFieldList.stream().map(cardFieldDtoConverter::toDto).collect(Collectors.toList()));
        return dto;
    }
}