package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import java.util.List;
import java.time.Instant;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id" })
public record ClassroomWithEventsDTO(
    String id,
    String description,
    String block,
    String floor,
    Integer capacity,
    String observation,
    Instant createdAt,
    Instant updatedAt,
    List<Event> events
) { }
