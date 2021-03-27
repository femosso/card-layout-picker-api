package com.example.demo.common.web.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private List<String> details;
    private int code;
}