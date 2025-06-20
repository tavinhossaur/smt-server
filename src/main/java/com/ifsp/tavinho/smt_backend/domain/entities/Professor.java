package com.ifsp.tavinho.smt_backend.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "professors")
@Getter @Setter
public class Professor extends BaseEntity {
    
    private String name;
    private String email;

    public Professor(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
}