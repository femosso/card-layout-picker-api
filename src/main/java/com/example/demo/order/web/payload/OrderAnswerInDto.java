package com.example.demo.order.web.payload;

import com.example.demo.card.web.payload.CardFieldInDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderAnswerInDto {
    private UUID id;
    private CardFieldInDto cardField;
    private String answer;
}