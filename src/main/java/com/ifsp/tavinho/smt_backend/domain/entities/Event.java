package com.ifsp.tavinho.smt_backend.domain.entities;

import java.time.LocalTime;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.ifsp.tavinho.smt_backend.shared.ApplicationProperties;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "events")
@Getter @Setter
public class Event {
    
    @Id
    private String id;
    private String description;
    private String weekday;
    
    @Field("start_time")
    private LocalTime startTime;
    @Field("end_time")
    private LocalTime endTime;
    @Field("classroom_id")
    private String classroomId;
    @Field("professor_id")
    private String professorId;
    @Field("course_id")
    private String courseId;
    @Field("discipline_id")
    private String disciplineId;

    @CreatedDate
    @Field(name = "created_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant updatedAt;

    public Event(String description, String weekday, LocalTime startTime, LocalTime endTime, String classroomId, String professorId, String disciplineId, String courseId) {
        this.description = description;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomId = classroomId;
        this.professorId = professorId;
        this.courseId = courseId;
        this.disciplineId = disciplineId;
    }

}
