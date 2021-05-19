package com.example.demo.card.web.controller;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.service.CardFieldService;
import com.example.demo.card.service.CardFieldTypeService;
import com.example.demo.card.service.CardService;
import com.example.demo.card.web.payload.CardFieldTypeOutDto;
import com.example.demo.card.web.payload.CardInDto;
import com.example.demo.card.web.payload.CardOutDto;
import com.example.demo.card.web.payload.converter.CardDtoConverter;
import com.example.demo.card.web.payload.converter.CardFieldDtoConverter;
import com.example.demo.card.web.payload.converter.CardFieldTypeDtoConverter;
import com.example.demo.common.web.payload.MessageResponse;
import com.example.demo.common.web.payload.table.TableDataInDto;
import com.example.demo.common.web.payload.table.TableDataOutDto;
import com.example.demo.common.web.payload.table.converter.TableDataDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private CardFieldService cardFieldService;
    @Autowired
    private CardFieldTypeService cardFieldTypeService;
    @Autowired
    private CardDtoConverter cardDtoConverter;
    @Autowired
    private CardFieldDtoConverter cardFieldDtoConverter;
    @Autowired
    private CardFieldTypeDtoConverter cardFieldTypeDtoConverter;
    @Autowired
    private TableDataDtoConverter tableDataDtoConverter;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<MessageResponse> create(@RequestPart("card") @Valid CardInDto cardInDto,
                                                  @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {
        Card card = cardService.save(
                cardDtoConverter.toEntity(cardInDto),
                file,
                cardInDto.getFields()
                        .stream()
                        .map(cardFieldDtoConverter::toEntity)
                        .collect(Collectors.toList())
        );
        if (card == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Card registered successfully!", null, 3));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") UUID id,
                                                  @RequestPart("card") @Valid CardInDto cardInDto,
                                                  @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file) {
        Card card = cardDtoConverter.toEntity(cardInDto);
        card.setId(id);
        card = cardService.save(
                card,
                file,
                cardInDto.getFields()
                        .stream()
                        .map(cardFieldDtoConverter::toEntity)
                        .collect(Collectors.toList())
        );
        if (card == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Card registered successfully!", null, 3));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<CardOutDto> get(@PathVariable("id") UUID id) {
        return cardService.get(id)
                .map(card -> cardDtoConverter.toDto(card, cardFieldService.listByCard(card)))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<CardOutDto>> list() {
        return ResponseEntity.ok(cardService.list()
                .stream()
                .map(card -> cardDtoConverter.toDto(card, cardFieldService.listByCard(card)))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<TableDataOutDto<CardOutDto>> listTableData(@Valid @RequestBody TableDataInDto input) {
        Page<Card> pageResult = cardService.list(tableDataDtoConverter.toPagingFilter(input));
        TableDataOutDto<CardOutDto> result = new TableDataOutDto<>();
        result.setData(pageResult
                .stream()
                .map(card -> cardDtoConverter.toDto(card, cardFieldService.listByCard(card)))
                .collect(Collectors.toList())
        );
        result.setTotal(pageResult.getTotalElements());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id) {
        Optional<Card> card = cardService.delete(id);
        if (!card.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new MessageResponse("Card deleted successfully!", null, 3));
    }

    @GetMapping("/field-types")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<List<CardFieldTypeOutDto>> fieldTypes() {
        return ResponseEntity.ok(cardFieldTypeService.list()
                .stream()
                .map(cardFieldTypeDtoConverter::toDto)
                .collect(Collectors.toList())
        );
    }
}