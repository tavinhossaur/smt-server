package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.FindProfessorWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListProfessorsWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListClassroomsFromCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FindProfessorWithEventsUseCase findProfessorWithEvents;
    private final ListProfessorsWithEventsUseCase listProfessorWithEvents;
    private final ListClassroomsFromCourseUseCase listClassrooms;
    private final ListCoursesUseCase listCourses;

    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    
    public ProfessorWithEventsDTO getProfessorWithWeekEventsList(String id) {
        Professor professor = this.professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
        return this.findProfessorWithEvents.execute(professor);
    }

    public List<ProfessorWithEventsDTO> getProfessorsWithWeekEventsListFromDayAndCourse(String weekday, String courseId) {
        if (weekday.isBlank() || weekday == null || courseId.isBlank() || courseId == null) {
            throw new AppError("Weekday and course values must be provided.", HttpStatus.BAD_REQUEST);
        }

        this.courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        return this.listProfessorWithEvents.execute(weekday, courseId);
    }

    public List<Classroom> getClassroomsFromFloorAndCourse(String floor, String courseId) {
        if (floor.isBlank() || floor == null || courseId.isBlank() || courseId == null) {
            throw new AppError("Floor and course values must be provided.", HttpStatus.BAD_REQUEST);
        }

        this.courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        return this.listClassrooms.execute(floor, courseId);
    }

    public List<Course> getAllCoursesList() {
        return this.listCourses.execute(null);
    }
}
