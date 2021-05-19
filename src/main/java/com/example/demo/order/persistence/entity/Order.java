package com.example.demo.order.persistence.entity;

import com.example.demo.card.persistence.entity.Card;
import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.user.persistence.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="`order`")
@Getter
@Setter
public class Order extends AbstractEntity {

    private String name;

    private String personId;

    private String employeeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card")
    private Card card;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status")
    private OrderStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}
