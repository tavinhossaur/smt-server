package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.CreateCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.UpdateCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.DeleteCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.FindCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CreateCourseUseCase createCourse;
    private final UpdateCourseUseCase updateCourse;
    private final DeleteCourseUseCase deleteCourse;
    private final FindCourseUseCase findCourse;
    private final ListCoursesUseCase listCourses;

    public ResponseEntity<Course> create(CourseDTO input) {
        return this.createCourse.execute(input);
    }

    public ResponseEntity<Course> update(CourseDTO input, String id) {
        return this.updateCourse.execute(input, id);
    }

    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.deleteCourse.execute(id);
    }

    public ResponseEntity<Course> find(String id) {
        return this.findCourse.execute(id);
    }

    public ResponseEntity<List<Course>> list() {
        return this.listCourses.execute(null);
    }
    
}
