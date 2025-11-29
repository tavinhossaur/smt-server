package com.ifsp.tavinho.smt_backend.application.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record DisciplineDTO(
    @NotBlank(message = "Name is required") String name, 
    @NotBlank(message = "Abbreviation is required") String abbreviation, 
    @NotBlank(message = "Course ID is required") String courseId
) { }
