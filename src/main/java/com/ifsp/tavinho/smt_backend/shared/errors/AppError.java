package com.ifsp.tavinho.smt_backend.shared.errors;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AppError extends RuntimeException {

    private final HttpStatus status;

    public AppError(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    
}