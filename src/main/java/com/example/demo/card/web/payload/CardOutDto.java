package com.example.demo.card.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CardOutDto extends MinimalCardOutDto {
    private List<CardFieldOutDto> fields;
    private Instant createdAt;
}
