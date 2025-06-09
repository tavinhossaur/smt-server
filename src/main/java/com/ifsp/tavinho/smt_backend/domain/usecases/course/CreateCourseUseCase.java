package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class CreateCourseUseCase implements UseCase<CourseDTO, Course> {

    @Autowired
    private CourseRepository repository;

    @Override
    public ResponseEntity<Course> execute(CourseDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(new Course(input.name())));
    }
    
}
