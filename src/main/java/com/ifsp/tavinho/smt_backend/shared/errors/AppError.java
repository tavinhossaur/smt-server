package com.ifsp.tavinho.smt_backend.shared.errors;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class AppError extends RuntimeException {

    private final HttpStatusCode statusCode;

    public AppError(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
}