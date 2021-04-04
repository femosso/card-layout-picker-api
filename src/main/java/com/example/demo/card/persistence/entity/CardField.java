package com.example.demo.card.persistence.entity;

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
public class CardField extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "card")
    private Card card;

    @Column(nullable = false)
    private String label;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type")
    private CardFieldType type;

}