package com.example.demo.common.web.payload.table;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableDataOutDto<T> {
    private List<T> data;
    private Long total = 0L;
}
