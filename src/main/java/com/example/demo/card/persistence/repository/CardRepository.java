package com.example.demo.card.persistence.repository;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.user.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    //@Query("SELECT c FROM Card c WHERE c.id = :uuid AND c.createdBy = :user")
    Optional<Card> findByIdAndCreatedBy(UUID uuid, User user);

    @Query("SELECT c FROM Card c WHERE c.createdBy = :user")
    Page<Card> findAllByUser(User user, Pageable pageable);
}