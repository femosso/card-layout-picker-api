package com.example.demo.card.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.file.persistence.entity.File;
import com.example.demo.user.persistence.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Card extends AbstractEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "file")
    private File file;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}