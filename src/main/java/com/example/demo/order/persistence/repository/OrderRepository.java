package com.example.demo.order.persistence.repository;

import com.example.demo.order.persistence.entity.Order;
import com.example.demo.user.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.createdBy = :user")
    Page<Order> findAllByUser(User user, Pageable pageable);

    Page<Order> findAllByStatusIdIn(Collection<UUID> statuses, Pageable pageable);

    List<Order> findByIdIn(Collection<UUID> ids);

    Optional<Order> findByIdAndStatusId(UUID id, UUID statusId);

    @Query("SELECT o FROM Order o" +
            " WHERE (COALESCE(:statuses, NULL) IS NULL OR o.status.id IN :statuses)" +
            " AND (:name IS NULL OR o.name LIKE %:name%)" +
            " AND ((:startInstant IS NULL OR :endInstant IS NULL) OR (o.createdAt BETWEEN :startInstant AND :endInstant))")
    Page<Order> findBySearch(Collection<UUID> statuses, String name, Instant startInstant, Instant endInstant, Pageable pageable);
}