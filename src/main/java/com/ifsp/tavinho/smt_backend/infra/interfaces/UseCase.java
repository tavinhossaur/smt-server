package com.ifsp.tavinho.smt_backend.infra.interfaces;

import org.springframework.http.ResponseEntity;

public interface UseCase<I, O> {
    
    ResponseEntity<O> execute(I input);

    default ResponseEntity<O> execute(I input, String id) {
        throw new UnsupportedOperationException("This use case does not support ID-based execution.");
    }

}
