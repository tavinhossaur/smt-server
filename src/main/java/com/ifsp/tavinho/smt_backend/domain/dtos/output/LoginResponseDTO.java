package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import java.util.List;

public record LoginResponseDTO(String id, String username, String email, List<String> authorities, String token, long expirationTime) { }
