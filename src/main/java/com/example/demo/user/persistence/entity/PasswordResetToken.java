package com.example.demo.user.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Entity
@Getter
@Setter
public class PasswordResetToken extends AbstractEntity {

    @Column(nullable = false)
    private Instant expireAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

}