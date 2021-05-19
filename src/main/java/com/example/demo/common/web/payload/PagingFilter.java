package com.example.demo.common.web.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class PagingFilter {
    private Pageable paging;
    private Map<String, String> searchMap;
    private Instant fromDate;
    private Instant toDate;
}
