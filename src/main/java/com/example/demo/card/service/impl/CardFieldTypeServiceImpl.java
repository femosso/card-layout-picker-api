package com.example.demo.card.service.impl;

import com.example.demo.card.persistence.entity.CardFieldType;
import com.example.demo.card.persistence.repository.CardFieldTypeRepository;
import com.example.demo.card.service.CardFieldTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardFieldTypeServiceImpl implements CardFieldTypeService {

    @Autowired
    private CardFieldTypeRepository cardFieldTypeRepository;

    @Override
    public List<CardFieldType> list() {
        return cardFieldTypeRepository.findAll();
    }
}