package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventDTO(
    @NotBlank(message = "Description is required") String description, 
    @NotBlank(message = "Weekday is required") String weekday, 
    @NotNull(message = "Start time is required") LocalTime startTime, 
    @NotNull(message = "End time is required") LocalTime endTime, 
    @NotBlank(message = "Classroom ID is required") String classroomId, 
    @NotBlank(message = "Professor ID is required") String professorId, 
    @NotBlank(message = "Course ID is required") String courseId, 
    @NotBlank(message = "Discipline ID is required") String disciplineId
) { }
