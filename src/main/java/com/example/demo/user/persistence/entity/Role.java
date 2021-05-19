package com.example.demo.user.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.user.domain.RoleType;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class Role extends AbstractEntity {
    public static final UUID ROLE_ADMIN = new UUID(0x958aab358f0811ebL, 0x8fcc0242ac110002L);
    public static final UUID ROLE_CLIENT = new UUID(0x9ae1dcde8f0811ebL, 0x8fcc0242ac110002L);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;

}
