package com.example.demo.order.persistence.entity;

import com.example.demo.card.persistence.entity.CardField;
import com.example.demo.common.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class OrderAnswer extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "`order`")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_field")
    private CardField cardField;

    @Column(nullable = false)
    private String answer;
}
