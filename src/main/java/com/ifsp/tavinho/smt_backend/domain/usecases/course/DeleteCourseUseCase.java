package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCourseUseCase implements UseCase<Course, Boolean> {

    private final CourseRepository repository;

    @Override
    public Boolean execute(Course course) {
        this.repository.delete(course);
        return true;
    }
    
}
