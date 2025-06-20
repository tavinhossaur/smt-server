package com.ifsp.tavinho.smt_backend.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "disciplines")
@Getter @Setter
public class Discipline extends BaseEntity {
    
    private String name;
    private String abbreviation;

    @Field("course_id")
    private String courseId;

    public Discipline(String name, String abbreviation, String courseId) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.courseId = courseId;
    }

}
