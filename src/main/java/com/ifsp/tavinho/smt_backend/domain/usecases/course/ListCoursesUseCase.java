package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListCoursesUseCase implements UseCase<Void, List<Course>> {

    private final CourseRepository repository;

    @Override
    public List<Course> execute(Void v) {
        return this.repository.findAllByOrderByNameAsc();
    }

}
