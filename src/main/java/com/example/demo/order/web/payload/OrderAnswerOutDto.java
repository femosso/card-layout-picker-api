package com.example.demo.order.web.payload;

import com.example.demo.card.web.payload.CardFieldOutDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderAnswerOutDto {
    private UUID id;
    private CardFieldOutDto cardField;
    private String answer;
}