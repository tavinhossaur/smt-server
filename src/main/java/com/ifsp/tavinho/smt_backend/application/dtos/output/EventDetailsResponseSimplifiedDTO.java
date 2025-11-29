package com.ifsp.tavinho.smt_backend.application.dtos.output;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;

@JsonPropertyOrder({ "id" })
public record EventDetailsResponseSimplifiedDTO(
    String id,
    String description,
    Weekday weekday,
    LocalTime startTime,
    LocalTime endTime,
    SimplifiedEntityDTO classroom,
    SimplifiedEntityDTO professor,
    SimplifiedEntityDTO course,
    SimplifiedEntityDTO discipline
) { }
