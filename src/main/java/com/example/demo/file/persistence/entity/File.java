package com.example.demo.file.persistence.entity;

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
public class File extends AbstractEntity {

    public enum Purpose {
        CARD_LAYOUT
    }

    private String originalName;

    @Column(nullable = false)
    private String contentType;

    private Long size;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private Purpose purpose;

}