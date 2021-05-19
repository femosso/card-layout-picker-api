package com.example.demo.common.web.payload.table;

import com.example.demo.common.web.payload.table.field.SearchFieldInDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TableDataInDto {
    private String sortColumn;
    private String sortDirection;
    private Integer page;
    private Integer pageSize;

    private List<SearchFieldInDto> searchTerms;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate toDate;

}