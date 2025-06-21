package com.ifsp.tavinho.smt_backend.domain.entities;

import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "events")
@Getter @Setter
public class Event extends BaseEntity {
    
    private String description;
    private Weekday weekday;
    
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

    public Event(String description, Weekday weekday, LocalTime startTime, LocalTime endTime, String classroomId, String professorId, String disciplineId, String courseId) {
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
