package com.example.demo.common.web.payload.table.converter;

import com.example.demo.common.web.payload.PagingFilter;
import com.example.demo.common.web.payload.table.TableDataInDto;
import com.example.demo.common.web.payload.table.field.SearchFieldInDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TableDataDtoConverter {

    public PagingFilter toPagingFilter(TableDataInDto dto) {
        if (dto == null) {
            return null;
        }

        PagingFilter pagingFilter = new PagingFilter();
        pagingFilter.setPaging(createPaging(dto));
        pagingFilter.setSearchMap(new HashMap<>());
        pagingFilter.setSearchMap(createSearchMap(dto));
        pagingFilter.setFromDate(dto.getFromDate() != null ?
                LocalDateTime.of(dto.getFromDate(), LocalTime.MIN).toInstant(ZoneOffset.UTC) :
                null
        );
        pagingFilter.setToDate(dto.getToDate() != null ?
                LocalDateTime.of(dto.getToDate(), LocalTime.MAX).toInstant(ZoneOffset.UTC) :
                Instant.now()
        );
        return pagingFilter;
    }

    private Pageable createPaging(TableDataInDto dto) {
        return PageRequest.of(dto.getPage() - 1, dto.getPageSize(), toSortOrder(dto));
    }

    private Sort toSortOrder(TableDataInDto dto) {
        if (StringUtils.hasText(dto.getSortColumn()) && StringUtils.hasText(dto.getSortDirection())) {
            return Sort.by(new Sort.Order(
                    "asc".equals(dto.getSortDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                    dto.getSortColumn()
            ));
        }
        return Sort.unsorted();
    }

    private Map<String, String> createSearchMap(TableDataInDto dto) {
        if (dto.getSearchTerms() == null) return null;
        return dto.getSearchTerms()
                .stream()
                .filter(searchFieldInDto -> Objects.nonNull(searchFieldInDto.getValue()))
                .collect(
                        Collectors.toMap(
                                SearchFieldInDto::getName,
                                SearchFieldInDto::getValue
                        )
                );
    }
}