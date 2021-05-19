package com.example.demo.order.web.payload;

import com.example.demo.card.web.payload.CardInDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderInDto {
    private UUID id;
    private String name;
    private String personId;
    private String employeeId;
    private UUID statusId;
    private CardInDto card;
    private List<OrderAnswerInDto> orderAnswers;
}
