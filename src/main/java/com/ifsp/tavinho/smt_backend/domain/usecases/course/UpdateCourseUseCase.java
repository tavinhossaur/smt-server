package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCourseUseCase implements UseCase<CourseDTO, Course> {

    private final CourseRepository repository;

    @Override
    public ResponseEntity<Course> execute(CourseDTO input, String id) {
        Course existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        if (input.name() != null) existing.setName(input.name());
        if (input.abbreviation() != null) existing.setAbbreviation(input.abbreviation());

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<Course> execute(CourseDTO input) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
