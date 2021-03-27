package com.example.demo.user.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User extends AbstractEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role")
    private Role role;

}