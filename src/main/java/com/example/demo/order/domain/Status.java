package com.example.demo.order.domain;

import com.example.demo.order.persistence.entity.OrderStatus;

import java.util.UUID;

public enum Status {
    WAITING_EMPLOYEE {
        @Override
        public UUID id() {
            return OrderStatus.WAITING_EMPLOYEE;
        }

        @Override
        public Status next() {
            return VALIDATING_FIELDS;
        }
    },
    VALIDATING_FIELDS {
        @Override
        public UUID id() {
            return OrderStatus.VALIDATING_FIELDS;
        }

        @Override
        public Status next() {
            return CREATED;
        }
    },
    CREATED {
        @Override
        public UUID id() {
            return OrderStatus.CREATED;
        }

        @Override
        public Status next() {
            return IN_PRODUCTION;
        }
    },
    IN_PRODUCTION {
        @Override
        public UUID id() {
            return OrderStatus.IN_PRODUCTION;
        }

        @Override
        public Status next() {
            return FINISHED;
        }
    },
    FINISHED {
        @Override
        public UUID id() {
            return OrderStatus.FINISHED;
        }

        @Override
        public Status next() {
            return this;
        }
    };

    public abstract UUID id();
    public abstract Status next();
}
