package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CardFieldTypeOutDto {
    private UUID id;
    private String name;
}