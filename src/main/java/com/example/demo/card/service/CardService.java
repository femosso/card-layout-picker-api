package com.example.demo.card.service;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.common.web.payload.PagingFilter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    Card save(Card card, MultipartFile file, List<CardField> cardFields);

    Optional<Card> get(UUID id);

    List<Card> list();

    Page<Card> list(PagingFilter pagingFilter);

    Optional<Card> delete(UUID id);
}
