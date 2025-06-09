package com.ifsp.tavinho.smt_backend.domain.dtos.output;

public record LoginResponseDTO(String id, String username, String email, Boolean isAdmin, String token, long expirationTime) { }
