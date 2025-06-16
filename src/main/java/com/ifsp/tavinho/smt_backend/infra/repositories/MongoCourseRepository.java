package com.ifsp.tavinho.smt_backend.infra.repositories;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCourseRepository extends MongoRepository<Course, String>, CourseRepository { }
