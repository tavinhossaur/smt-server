package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public record SimplifiedEntityDTO(
    String id,
    String description
) { }
