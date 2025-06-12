package com.ifsp.tavinho.smt_backend.domain.entities;

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

@Document(collection = "professors")
@Getter @Setter
public class Professor {
    
    @Id
    private String id;
    private String name;
    private String email;

    @CreatedDate
    @Field(name = "created_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant updatedAt;

    public Professor(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
}