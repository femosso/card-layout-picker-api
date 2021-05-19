package com.example.demo.order.web.controller;

import com.example.demo.common.web.payload.table.TableDataInDto;
import com.example.demo.common.web.payload.MessageResponse;
import com.example.demo.common.web.payload.table.TableDataOutDto;
import com.example.demo.common.web.payload.table.converter.TableDataDtoConverter;
import com.example.demo.order.persistence.entity.Order;
import com.example.demo.order.service.OrderAnswerService;
import com.example.demo.order.service.OrderService;
import com.example.demo.order.service.OrderStatusService;
import com.example.demo.order.web.payload.OrderInDto;
import com.example.demo.order.web.payload.OrderOutDto;
import com.example.demo.order.web.payload.OrderStatusOutDto;
import com.example.demo.order.web.payload.ValidatePersonIdDto;
import com.example.demo.order.web.payload.converter.OrderAnswerDtoConverter;
import com.example.demo.order.web.payload.converter.OrderDtoConverter;
import com.example.demo.order.web.payload.converter.OrderStatusDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderAnswerService orderAnswerService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private OrderDtoConverter orderDtoConverter;
    @Autowired
    private OrderAnswerDtoConverter orderAnswerDtoConverter;
    @Autowired
    private OrderStatusDtoConverter orderStatusDtoConverter;
    @Autowired
    private TableDataDtoConverter tableDataDtoConverter;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody OrderInDto orderInDto) {
        Order order = orderService.save(orderDtoConverter.toEntity(orderInDto), null);
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Order registered successfully!", null, 3));
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") UUID id,
                                                  @Valid @RequestBody OrderInDto orderInDto) {
        Order order = orderDtoConverter.toEntity(orderInDto);
        order.setId(id);
        order = orderService.save(
                order,
                orderInDto.getOrderAnswers()
                        .stream()
                        .map(orderAnswerDtoConverter::toEntity)
                        .collect(Collectors.toList())
        );
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Order registered successfully!", null, 3));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutDto> get(@PathVariable("id") UUID id) {
        return orderService.get(id)
                .map(order -> orderDtoConverter.toDto(order, orderAnswerService.listByOrder(order)))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<TableDataOutDto<OrderOutDto>> list(@Valid @RequestBody TableDataInDto input) {
        Page<Order> out = orderService.list(tableDataDtoConverter.toPagingFilter(input));

        TableDataOutDto<OrderOutDto> result = new TableDataOutDto<>();
        result.setData(out
                .stream()
                .map(order -> orderDtoConverter.toDto(order, orderAnswerService.listByOrder(order)))
                .collect(Collectors.toList()));
        result.setTotal(out.getTotalElements());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/status")
    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')") // FIXME - should be only ADMIN
    public ResponseEntity<MessageResponse> updateStatuses(@Valid @RequestBody List<OrderInDto> orderInDtoList) {
        orderService.updateStatuses(orderInDtoList
                .stream()
                .map(orderInDto -> orderDtoConverter.toEntity(orderInDto))
                .collect(Collectors.toList())
        );
        return ResponseEntity.ok(new MessageResponse("Order update successfully!", null, 3));
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<String> validatePersonId(@PathVariable("id") UUID id,
                                                   @Valid @RequestBody ValidatePersonIdDto validatePersonIdDto) {
        return new ResponseEntity<>("",
                orderService.validatePersonId(id, validatePersonIdDto.getPersonId()) ?
                        HttpStatus.OK :
                        HttpStatus.FORBIDDEN
        );
    }

    @GetMapping("/status-types")
    @PreAuthorize("hasRole('ROLE_CLIENT') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderStatusOutDto>> statusTypes() {
        return ResponseEntity.ok(orderStatusService.list()
                .stream()
                .map(orderStatusDtoConverter::toDto)
                .collect(Collectors.toList())
        );
    }
}
