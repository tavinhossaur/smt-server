package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.CreateCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.UpdateCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.DeleteCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.FindCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CreateCourseUseCase createCourse;
    private final UpdateCourseUseCase updateCourse;
    private final DeleteCourseUseCase deleteCourse;
    private final FindCourseUseCase findCourse;
    private final ListCoursesUseCase listCourses;

    public Course create(CourseDTO input) {
        return this.createCourse.execute(input);
    }

    public Course update(CourseDTO input, String id) {
        Course course = this.findCourse.execute(id);
        return this.updateCourse.execute(input, course);
    }

    public Boolean delete(String id) {
        Course course = this.findCourse.execute(id);
        return this.deleteCourse.execute(course);
    }

    public Course find(String id) {
        return this.findCourse.execute(id);
    }

    public List<Course> list() {
        return this.listCourses.execute(null);
    }
    
}
