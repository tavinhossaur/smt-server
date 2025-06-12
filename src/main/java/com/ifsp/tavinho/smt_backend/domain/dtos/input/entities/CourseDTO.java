package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import jakarta.validation.constraints.NotBlank;

public record CourseDTO(
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "Abbreviation is required") String abbreviation
) { }
