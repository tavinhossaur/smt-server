package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindCourseUseCase implements UseCase<String, Course> {

    private final CourseRepository repository;

    @Override
    public Course execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }
    
}
