package com.example.demo.card.web.payload.converter;

import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.persistence.entity.CardFieldType;
import com.example.demo.card.web.payload.CardFieldInDto;
import com.example.demo.card.web.payload.CardFieldOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardFieldDtoConverter {

    @Autowired
    public CardFieldTypeDtoConverter cardFieldTypeDtoConverter;

    public CardField toEntity(CardFieldInDto dto) {
        if (dto == null) {
            return null;
        }

        CardField entity = new CardField();
        entity.setId(dto.getId());
        entity.setLabel(dto.getName());
        if (dto.getFieldTypeId() != null) {
            CardFieldType cardFieldType = new CardFieldType();
            cardFieldType.setId(dto.getFieldTypeId());
            entity.setType(cardFieldType);
        }
        return entity;
    }

    public CardFieldOutDto toDto(CardField entity) {
        if (entity == null) {
            return null;
        }

        CardFieldOutDto dto = new CardFieldOutDto();
        dto.setId(entity.getId());
        dto.setName(entity.getLabel());
        dto.setType(cardFieldTypeDtoConverter.toDto(entity.getType()));
        return dto;
    }
}
