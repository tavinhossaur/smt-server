package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventDTO(
    @NotBlank(message = "Description is required") String description, 
    @NotNull(message = "Weekday is required") Weekday weekday, 
    @NotBlank(message = "Start time is required") String startTime, 
    @NotBlank(message = "End time is required") String endTime, 
    @NotBlank(message = "Classroom ID is required") String classroomId, 
    @NotBlank(message = "Professor ID is required") String professorId, 
    @NotBlank(message = "Discipline ID is required") String disciplineId,
    @NotBlank(message = "Course ID is required") String courseId
) { }
