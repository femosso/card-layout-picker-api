package com.example.demo.user.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PasswordResetToken extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    @Column(nullable = false)
    private UUID token;

    @Column(nullable = false)
    private Instant expireAt;

}