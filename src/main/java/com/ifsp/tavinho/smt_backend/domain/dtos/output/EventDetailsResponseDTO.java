package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;

@JsonPropertyOrder({ "id" })
public record EventDetailsResponseDTO(
    String id,
    String description,
    Weekday weekday,
    LocalTime startTime,
    LocalTime endTime,
    Classroom classroom,
    Professor professor,
    Course course,
    Discipline discipline
) { }
