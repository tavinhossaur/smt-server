package com.ifsp.tavinho.smt_backend.infra.interfaces;

public interface UseCase<I, O> {
    
    O execute(I input);

    default O execute(I input, String id) {
        throw new UnsupportedOperationException("This use case does not support ID-based execution.");
    }

    default O execute(I input, O existing) {
        throw new UnsupportedOperationException("An existing entity is required for this use case.");
    }
}
