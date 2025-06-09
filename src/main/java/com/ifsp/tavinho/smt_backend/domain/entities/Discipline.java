package com.ifsp.tavinho.smt_backend.domain.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "disciplines")
@Getter @Setter
public class Discipline {
    
    @Id
    private String id;
    private String name;
    
    @Field("course_id")
    private String courseId;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Date updatedAt;
    
    public Discipline(String name, String courseId) {
        this.name = name;
        this.courseId = courseId;
    }

}
