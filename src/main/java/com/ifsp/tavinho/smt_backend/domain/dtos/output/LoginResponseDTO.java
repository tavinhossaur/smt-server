package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public record LoginResponseDTO(
    String id, 
    String username, 
    String email, 
    List<String> authorities, 
    String token, 
    long expirationTime
) { }
