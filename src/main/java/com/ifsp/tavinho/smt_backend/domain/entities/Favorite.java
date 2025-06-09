package com.ifsp.tavinho.smt_backend.domain.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "favorites")
@Getter @Setter
public class Favorite {
    
    @Id
    private String id;

    @Field("user_id")
    private String userId;
    @Field("professor_id")
    private String professorId;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    private Date updatedAt;

    public Favorite(String userId, String professorId) {
        this.userId = userId;
        this.professorId = professorId;
    }

}
