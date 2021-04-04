package com.example.demo.user.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.user.domain.RoleType;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;

}
