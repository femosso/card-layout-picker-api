package com.example.demo.card.service.impl;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.card.persistence.repository.CardFieldRepository;
import com.example.demo.card.service.CardFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardFieldServiceImpl implements CardFieldService {

    @Autowired
    private CardFieldRepository cardFieldRepository;

    @Override
    @Transactional
    public CardField save(Card card, CardField cardField) {
        cardField.setCard(card);
        return cardFieldRepository.save(cardField);
    }

    @Override
    @Transactional
    public List<CardField> saveAll(Card card, List<CardField> cardFieldList) {
        cardFieldList.forEach(cardField -> cardField.setCard(card));
        return cardFieldRepository.saveAll(cardFieldList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardField> listByCard(Card card) {
        return cardFieldRepository.findByCardId(card.getId());
    }

    @Override
    @Transactional
    public List<CardField> deleteByCard(Card card) {
        return cardFieldRepository.deleteByCardId(card.getId());
    }
}