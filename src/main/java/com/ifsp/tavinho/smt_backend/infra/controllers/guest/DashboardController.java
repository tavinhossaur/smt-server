package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.domain.usecases.course.ListCoursesUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.FindProfessorWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListClassroomsFromCourseUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.dashboard.ListProfessorsWithEventsUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.DASHBOARD_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFESSORS;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.CLASSROOMS;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.COURSES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BY_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(DASHBOARD_ROUTE)
public class DashboardController {
    
    private final FindProfessorWithEventsUseCase findProfessorWithEvents;
    private final ListProfessorsWithEventsUseCase listProfessorWithEvents;
    private final ListClassroomsFromCourseUseCase listClassrooms;
    private final ListCoursesUseCase listCourses;

    @GetMapping(PROFESSORS + BY_ID)
    public ResponseEntity<ProfessorWithEventsDTO> getProfessorWithWeekEventsList(@PathVariable String id) {
        return this.findProfessorWithEvents.execute(id);
    }
    
    @GetMapping(PROFESSORS)
    public ResponseEntity<List<ProfessorWithEventsDTO>> getProfessorsWithWeekEventsListFromDayAndCourse(@RequestParam String weekday, @RequestParam String course) {
        return this.listProfessorWithEvents.execute(weekday, course);
    }
    
    @GetMapping(CLASSROOMS)
    public ResponseEntity<List<Classroom>> getClassroomsFromFloorAndCourse(@RequestParam String floor, @RequestParam String course) {
        return this.listClassrooms.execute(floor, course);
    }

    @GetMapping(COURSES)
    public ResponseEntity<List<Course>> getAllCoursesList() {
        return this.listCourses.execute(null);
    }
    
}
