package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorDTO(
    @NotBlank(message = "Name is required") String name, 
    @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email
) { }
