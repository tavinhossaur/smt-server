package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.CreateCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.DeleteCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.FindCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.UpdateCourseUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_COURSES;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_COURSES)
public class CourseController implements CrudOperations<CourseDTO, Course> {

    private final CreateCourseUseCase createCourse;
    private final UpdateCourseUseCase updateCourse;
    private final DeleteCourseUseCase deleteCourse;
    private final FindCourseUseCase findCourse;
    private final ListCoursesUseCase listCourses;

    @Override
    public ResponseEntity<Course> create(@Valid CourseDTO input) {
        return this.createCourse.execute(input);
    }

    @Override
    public ResponseEntity<Course> update(CourseDTO input, String id) {
        return this.updateCourse.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.deleteCourse.execute(id);
    }

    @Override
    public ResponseEntity<Course> find(String id) {
        return this.findCourse.execute(id);
    }

    @Override
    public ResponseEntity<List<Course>> list() {
        return this.listCourses.execute(null);
    }
    
}
