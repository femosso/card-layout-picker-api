package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CardInDto {
    private UUID id;
    private List<CardFieldInDto> fields;
}
