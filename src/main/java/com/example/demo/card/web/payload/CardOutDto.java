package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CardOutDto {
    private UUID id;
    private String imagePath;
    private List<CardFieldOutDto> fields;
}
