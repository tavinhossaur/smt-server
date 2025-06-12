package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record UserDTO(
    @NotBlank(message = "Name is required") String fullName, 
    @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
    Boolean isAdmin
) { }