package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListCoursesUseCase implements UseCase<Void, List<Course>> {

    private final CourseRepository repository;

    @Override
    public ResponseEntity<List<Course>> execute(Void v) {
        return ResponseEntity.ok(this.repository.findAll());
    }
    
}
