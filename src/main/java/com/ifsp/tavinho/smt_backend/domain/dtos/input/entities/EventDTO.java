package com.ifsp.tavinho.smt_backend.domain.dtos.input.entities;

import jakarta.validation.constraints.NotBlank;

public record EventDTO(
    @NotBlank(message = "Description is required") String description, 
    @NotBlank(message = "Weekday is required") String weekday, 
    @NotBlank(message = "Start time is required") String startTime, 
    @NotBlank(message = "End time is required") String endTime, 
    @NotBlank(message = "Classroom ID is required") String classroomId, 
    @NotBlank(message = "Professor ID is required") String professorId, 
    @NotBlank(message = "Course ID is required") String courseId, 
    @NotBlank(message = "Discipline ID is required") String disciplineId
) { }
