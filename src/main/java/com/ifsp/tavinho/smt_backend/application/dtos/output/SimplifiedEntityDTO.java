package com.ifsp.tavinho.smt_backend.application.dtos.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public record SimplifiedEntityDTO(
    String id,
    String description
) { }
