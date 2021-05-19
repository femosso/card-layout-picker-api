package com.example.demo.order.web.payload;

import com.example.demo.card.web.payload.MinimalCardOutDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderOutDto {
    private UUID id;
    private String name;
    private String personId;
    private String employeeId;
    private UUID statusId;
    private Instant createdAt;
    private MinimalCardOutDto card;
    private List<OrderAnswerOutDto> orderAnswers;
}