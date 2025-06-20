package com.ifsp.tavinho.smt_backend.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "classrooms")
@Getter @Setter
public class Classroom extends BaseEntity {
    
    private String description;
    private String block;
    private String floor;
    private Integer capacity;
    private String observation;
    
    public Classroom(String description, String block, String floor, Integer capacity, String observation) {
        this.description = description;
        this.block = block;
        this.floor = floor;
        this.capacity = capacity;
        this.observation = observation;
    }

}
