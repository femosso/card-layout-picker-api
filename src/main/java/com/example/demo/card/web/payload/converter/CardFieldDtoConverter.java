package com.example.demo.card.web.payload.converter;

import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.persistence.repository.CardFieldTypeRepository;
import com.example.demo.card.web.payload.CardFieldInDto;
import com.example.demo.card.web.payload.CardFieldOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardFieldDtoConverter {

    @Autowired
    public CardFieldTypeRepository cardFieldTypeRepository;

    public CardField toEntity(CardFieldInDto dto) {
        if (dto == null) {
            return null;
        }

        CardField entity = new CardField();
        entity.setLabel(dto.getName());
        entity.setType(cardFieldTypeRepository.findById(dto.getFieldTypeId())
                .orElseThrow(() -> new RuntimeException("Error: Card field type not found."))
        );
        return entity;
    }

    public CardFieldOutDto toDto(CardField entity) {
        if (entity == null) {
            return null;
        }

        CardFieldOutDto dto = new CardFieldOutDto();
        dto.setName(entity.getLabel());
        dto.setFieldTypeId(entity.getType().getId());
        return dto;
    }
}
