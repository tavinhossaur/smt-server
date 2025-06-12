package com.ifsp.tavinho.smt_backend.infra.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final String message) {
        super(message);
    }
}

