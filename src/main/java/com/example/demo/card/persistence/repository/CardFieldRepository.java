package com.example.demo.card.persistence.repository;

import com.example.demo.card.persistence.entity.CardField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardFieldRepository extends JpaRepository<CardField, UUID> {

    List<CardField> findByCardId(UUID cardId);

    List<CardField> deleteByCardId(UUID cardId);

}