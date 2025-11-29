package com.ifsp.tavinho.smt_backend.application.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record UpdateFavoritesDTO(
    @NotBlank(message = "Professor ID is required") String professorId
) { }
