package com.example.demo.order.web.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderStatusOutDto {
    private UUID id;
    private String name;
}