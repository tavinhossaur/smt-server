package com.ifsp.tavinho.smt_backend.application.dtos.output;

import java.util.List;
import java.time.Instant;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public record ProfessorWithEventsDTO(
    String id,
    String name,
    String email,
    Instant createdAt,
    Instant updatedAt,
    List<Event> events
) { }
