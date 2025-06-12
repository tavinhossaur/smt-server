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

@Document(collection = "classrooms")
@Getter @Setter
public class Classroom {
    
    @Id
    private String id;
    private String description;
    private String block;
    private String floor;
    private Integer capacity;
    private String observation;

    @CreatedDate
    @Field(name = "created_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant createdAt;

    @LastModifiedDate
    @Field(name = "updated_at")
    @JsonFormat(pattern = ApplicationProperties.TIMESTAMP_PATTERN, timezone = ApplicationProperties.TIMEZONE)
    private Instant updatedAt;
    
    public Classroom(String description, String block, String floor, Integer capacity, String observation) {
        this.description = description;
        this.block = block;
        this.floor = floor;
        this.capacity = capacity;
        this.observation = observation;
    }

}
