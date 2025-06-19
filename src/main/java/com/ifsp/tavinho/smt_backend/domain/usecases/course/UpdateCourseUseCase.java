package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCourseUseCase implements UseCase<CourseDTO, Course> {

    private final CourseRepository repository;

    @Override
    public Course execute(CourseDTO input, Course existing) {
        if (input.name() != null) existing.setName(input.name());
        if (input.abbreviation() != null) existing.setAbbreviation(input.abbreviation());

        return this.repository.save(existing);
    }

    @Override
    public Course execute(CourseDTO input) {
        throw new UnsupportedOperationException("An existing course is required for update.");
    }
    
}
