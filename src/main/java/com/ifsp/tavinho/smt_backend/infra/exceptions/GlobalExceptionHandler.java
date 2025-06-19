package com.ifsp.tavinho.smt_backend.infra.exceptions;

import org.apache.catalina.connector.ClientAbortException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppError.class)
    public ResponseEntity<ServerApiResponse<Void>> handleAppError(AppError error) {
        return ResponseEntity.status(error.getStatus())
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .error(error.getStatus().getReasonPhrase())
                    .message(error.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMsg = new StringBuilder();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMsg.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        return ResponseEntity.badRequest().body(
            ServerApiResponse.<Void>builder()
                .status(Status.ERROR)
                .error("Object validation error")
                .message(errorMsg.toString().strip())
                .build()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ServerApiResponse<Void>> handleEntityNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .error("Entity not found")
                    .message(ex.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<ServerApiResponse<Void>> handleClientAbortException(Exception ex) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .error("Payload too large")
                    .message(ex.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerApiResponse<Void>> handleHttpMessageNotReadable(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .error("Invalid request body")
                    .message(ex.getMessage())
                    .build()
            );
    }

    @ExceptionHandler({ AuthenticationException.class, JWTVerificationException.class })
    public ResponseEntity<ServerApiResponse<String>> handleAuthenticationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ServerApiResponse.<String>builder()
                    .status(Status.ERROR)
                    .error("Authentication failed")
                    .message(ex.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerApiResponse<Void>> handleGenericError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.ERROR)
                    .error("Internal Server Error")
                    .message(ex.getMessage())
                    .build()
            );
    }
}
