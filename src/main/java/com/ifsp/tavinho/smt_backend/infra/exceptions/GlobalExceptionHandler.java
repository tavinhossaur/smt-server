package com.ifsp.tavinho.smt_backend.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppError.class)
    public ResponseEntity<ApiResponse<Void>> handleAppError(AppError error) {
        return ResponseEntity.status(error.getStatusCode())
            .body(
                ApiResponse.<Void>builder()
                    .status("error")
                    .message(error.getMessage()
                ).build()
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericError(Exception error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.<Void>builder()
                    .status("error")
                    .message(error.getMessage()
                ).build()
            );
    }
}
