package com.example.demo.card.persistence.repository;

import com.example.demo.card.persistence.entity.CardFieldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardFieldTypeRepository extends JpaRepository<CardFieldType, UUID> {

    Optional<CardFieldType> findByType(CardFieldType.Type type);

}