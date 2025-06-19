package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.FindProfessorWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListProfessorsWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListClassroomsFromCourseUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final FindProfessorWithEventsUseCase findProfessorWithEvents;
    private final ListProfessorsWithEventsUseCase listProfessorWithEvents;
    private final ListClassroomsFromCourseUseCase listClassrooms;
    private final ListCoursesUseCase listCourses;

    public ResponseEntity<ProfessorWithEventsDTO> getProfessorWithWeekEventsList(String id) {
        return this.findProfessorWithEvents.execute(id);
    }

    public ResponseEntity<List<ProfessorWithEventsDTO>> getProfessorsWithWeekEventsListFromDayAndCourse(String weekday, String course) {
        return this.listProfessorWithEvents.execute(weekday, course);
    }

    public ResponseEntity<List<Classroom>> getClassroomsFromFloorAndCourse(String floor, String course) {
        return this.listClassrooms.execute(floor, course);
    }

    public ResponseEntity<List<Course>> getAllCoursesList() {
        return this.listCourses.execute(null);
    }
}
