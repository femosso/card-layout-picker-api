package com.example.demo.card.service;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.card.persistence.entity.CardField;

import java.util.List;

public interface CardFieldService {

    CardField save(Card card, CardField cardField);

    List<CardField> saveAll(Card card, List<CardField> cardFieldList);

    List<CardField> listByCard(Card card);

    List<CardField> deleteByCard(Card card);

}