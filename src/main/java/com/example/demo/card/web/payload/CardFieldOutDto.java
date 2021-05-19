package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CardFieldOutDto {
    private UUID id;
    private String name;
    private CardFieldTypeOutDto type;
}
