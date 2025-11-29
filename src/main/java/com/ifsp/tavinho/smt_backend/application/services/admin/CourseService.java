package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EventRepository eventRepository;

    public Course create(CourseDTO input) {
        return this.courseRepository.save(new Course(input.name(), input.abbreviation()));
    }

    public Course update(CourseDTO input, String id) {
        Course existing = this.find(id);

        if (input.name() != null) existing.setName(input.name());
        if (input.abbreviation() != null) existing.setAbbreviation(input.abbreviation());

        return this.courseRepository.save(existing);
    }

    public Boolean delete(String id) {
        Course course = this.find(id);

        if (this.eventRepository.existsByCourseId(course.getId())) {
            throw new AppError("Course could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        this.courseRepository.delete(course);
        return true;
    }

    public Course find(String id) {
        return this.courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    public List<Course> list() {
        return this.courseRepository.findAllByOrderByNameAsc();
    }
    
}
