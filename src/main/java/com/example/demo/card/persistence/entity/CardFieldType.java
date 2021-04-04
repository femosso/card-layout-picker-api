package com.example.demo.card.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class CardFieldType extends AbstractEntity {

    public enum Type {
        TEXT, NUMBER, DATE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

}
