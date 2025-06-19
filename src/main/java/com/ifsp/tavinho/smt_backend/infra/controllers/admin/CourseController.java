package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.application.services.CourseService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_COURSES;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_COURSES)
public class CourseController implements EntityController<CourseDTO, Course> {

    private final CourseService courseService;

    @Override
    public ResponseEntity<Course> create(@Valid CourseDTO input) {
        return this.courseService.create(input);
    }

    @Override
    public ResponseEntity<Course> update(CourseDTO input, String id) {
        return this.courseService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.courseService.delete(id);
    }

    @Override
    public ResponseEntity<Course> find(String id) {
        return this.courseService.find(id);
    }

    @Override
    public ResponseEntity<List<Course>> list() {
        return this.courseService.list();
    }
    
}
