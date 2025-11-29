package com.ifsp.tavinho.smt_backend.application.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordDTO(
    @NotBlank(message = "Current password is required") String currentPassword, 
    @NotBlank(message = "New password is required") String newPassword
) { }
