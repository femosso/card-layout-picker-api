package com.example.demo.order.persistence.entity;

import com.example.demo.common.persistence.entity.AbstractEntity;
import com.example.demo.order.domain.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderStatus extends AbstractEntity {
    public static final UUID WAITING_EMPLOYEE = new UUID(0x36e3b5b19b0d11ebL, 0x970a0242ac110002L);
    public static final UUID VALIDATING_FIELDS = new UUID(0x3b1bb6fd9b0d11ebL, 0x970a0242ac110002L);
    public static final UUID CREATED = new UUID(0x3f7813a09b0d11ebL, 0x970a0242ac110002L);
    public static final UUID IN_PRODUCTION = new UUID(0x41dfc9239b0d11ebL, 0x970a0242ac110002L);
    public static final UUID FINISHED = new UUID(0x4465d4499b0d11ebL, 0x970a0242ac110002L);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status name;

}
