package com.ifsp.tavinho.smt_backend.application.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record CourseDTO(
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "Abbreviation is required") String abbreviation
) { }
