package com.ifsp.tavinho.smt_backend.application.dtos.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public record ClassroomDTO(
    @NotBlank(message = "Description is required") String description,
    @NotBlank(message = "Block is required") String block, 
    @NotBlank(message = "Floor is required") String floor, 
    @NotNull(message = "Capacity is required") @Min(value = 1, message = "Capacity must be greater than 0") Integer capacity, 
    String observation
) { }
