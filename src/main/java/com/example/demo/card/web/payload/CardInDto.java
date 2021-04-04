package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardInDto {
    private List<CardFieldInDto> fields;
}
