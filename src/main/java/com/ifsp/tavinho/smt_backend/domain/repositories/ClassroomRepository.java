package com.ifsp.tavinho.smt_backend.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;

@Repository
public interface ClassroomRepository extends MongoRepository<Classroom, String> { }
