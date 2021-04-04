package com.example.demo.card.service.impl;

import com.example.demo.auth.service.AuthService;
import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.persistence.repository.CardRepository;
import com.example.demo.card.service.CardFieldService;
import com.example.demo.card.service.CardService;
import com.example.demo.card.web.payload.converter.CardDtoConverter;
import com.example.demo.card.web.payload.converter.CardFieldDtoConverter;
import com.example.demo.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardFieldService cardFieldService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CardDtoConverter cardDtoConverter;
    @Autowired
    private CardFieldDtoConverter cardFieldDtoConverter;

    @Override
    @Transactional
    public Card save(Card card, MultipartFile multipartFile, List<CardField> cardFields) {
        UUID cardId = card.getId();
        if (cardId == null) {
            card.setCreatedAt(Instant.now());
            card.setUpdatedAt(card.getCreatedAt());
            card.setCreatedBy(authService.currentUser());
            card.setFile(fileService.upload(null, multipartFile));
            cardRepository.save(card);
            cardFieldService.saveAll(card, cardFields);
            return card;
        }
        Card entity = get(cardId).orElse(null);
        if (entity == null) return null;
        updateFields(card, entity, multipartFile, cardFields);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> get(UUID id) {
        return cardRepository.findById(id, authService.currentUser());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> list() {
        return cardRepository.findAllByUser(authService.currentUser());
    }

    @Override
    @Transactional
    public Optional<Card> delete(UUID id) {
        Optional<Card> card = get(id);
        if (card.isPresent()) {
            cardFieldService.deleteByCard(card.get());
            cardRepository.deleteById(id);
        }
        return card;
    }

    private void updateFields(Card from, Card to, MultipartFile multipartFile, List<CardField> cardFields) {
        cardFieldService.deleteByCard(from);
        cardFieldService.saveAll(from, cardFields);
        to.setFile(fileService.upload(to.getFile(), multipartFile));
        to.setUpdatedAt(Instant.now());
    }
}