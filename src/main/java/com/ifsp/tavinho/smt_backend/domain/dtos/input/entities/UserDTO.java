package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

public record UserDTO(String username, String email, Boolean isProfessor, Boolean isAdmin) { }