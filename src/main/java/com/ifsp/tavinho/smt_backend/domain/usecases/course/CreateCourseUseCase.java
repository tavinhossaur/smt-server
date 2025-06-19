package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCourseUseCase implements UseCase<CourseDTO, Course> {

    private final CourseRepository repository;

    @Override
    public Course execute(CourseDTO input) {
        return this.repository.save(new Course(input.name(), input.abbreviation()));
    }
    
}
