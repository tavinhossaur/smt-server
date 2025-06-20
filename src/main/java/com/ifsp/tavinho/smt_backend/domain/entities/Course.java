package com.ifsp.tavinho.smt_backend.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "courses")
@Getter @Setter
public class Course extends BaseEntity {
    
    private String name;
    private String abbreviation;

    public Course(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

}
