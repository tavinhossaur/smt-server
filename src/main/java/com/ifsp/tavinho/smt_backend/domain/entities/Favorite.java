package com.ifsp.tavinho.smt_backend.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "favorites")
@Getter @Setter
public class Favorite extends BaseEntity {
    
    @Field("user_id")
    private String userId;
    @Field("professor_id")
    private String professorId;

    public Favorite(String userId, String professorId) {
        this.userId = userId;
        this.professorId = professorId;
    }

}
