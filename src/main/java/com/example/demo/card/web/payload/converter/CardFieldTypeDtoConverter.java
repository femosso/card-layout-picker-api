package com.example.demo.card.web.payload.converter;

import com.example.demo.card.persistence.entity.CardFieldType;
import com.example.demo.card.web.payload.CardFieldTypeOutDto;
import org.springframework.stereotype.Component;

@Component
public class CardFieldTypeDtoConverter {

    public CardFieldTypeOutDto toDto(CardFieldType entity) {
        if (entity == null) {
            return null;
        }

        CardFieldTypeOutDto dto = new CardFieldTypeOutDto();
        dto.setId(entity.getId());
        dto.setName(entity.getType().name());
        return dto;
    }
}
