package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import java.time.LocalTime;

public record EventDTO(String description, String weekday, LocalTime startTime, LocalTime endTime, String classroomId, String professorId, String courseId, String disciplineId) { }
