package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ClassroomWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.DisciplineDetailsResponseSimplifiedDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseSimplifiedDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.SearchQueryResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.application.services.guest.DashboardService;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.DASHBOARD_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFESSORS;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.CLASSROOMS;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.COURSES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.EVENTS;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BY_ID;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.SEARCH;

@Tag(name = "Dashboard (Guest)", description = "Endpoints for dashboard data access, such as professors, classrooms, courses and events.")
@RestController
@RequiredArgsConstructor
@RequestMapping(DASHBOARD_ROUTE)
public class DashboardController {
    
    private final DashboardService dashboardService;

    @Operation(summary = "Get detailed event info", description = "Retrieves a detailed event info by event ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event info successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventDetailsResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Event ID was not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(EVENTS + BY_ID)
    public ResponseEntity<EventDetailsResponseDTO> getDetailedEventInfo(@PathVariable String id) {
        return ResponseEntity.ok(this.dashboardService.getDetailedEventInfo(id));
    }

    @Operation(summary = "Get professor with events", description = "Retrieves a professor and their events by professor ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor and events successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfessorWithEventsDTO.class))),
        @ApiResponse(responseCode = "400", description = "Professor ID was not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Professor not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(PROFESSORS + BY_ID)
    public ResponseEntity<ProfessorWithEventsDTO> getProfessorWithWeekEventsList(@PathVariable String id) {
        return ResponseEntity.ok(this.dashboardService.getProfessorWithWeekEventsList(id));
    }

    @Operation(summary = "List events with detailed info", description = "Lists all events and their professors, classrooms, courses and disciplines.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of events with detailed info successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(EVENTS)
    public ResponseEntity<List<EventDetailsResponseSimplifiedDTO>> getEventsWithDetailedInfo() {
        return ResponseEntity.ok(this.dashboardService.listEventsWithDetailedInfo());
    }

    @Operation(summary = "List disciplines with courses", description = "Lists all disciplines and their courses.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of disciplines with courses successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(EVENTS)
    public ResponseEntity<List<DisciplineDetailsResponseSimplifiedDTO>> getDisciplinesWithCourses() {
        return ResponseEntity.ok(this.dashboardService.listDisciplinesWithCourses());
    }
    
    @Operation(summary = "List professors with events", description = "Lists all professors and their events filtered by weekday and course.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of professors and events successfully returned."),
        @ApiResponse(responseCode = "400", description = "Weekday or course values were not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(PROFESSORS)
    public ResponseEntity<List<ProfessorWithEventsDTO>> getProfessorsWithWeekEventsListFromDayAndCourse(@RequestParam String weekday, @RequestParam String course) {
        return ResponseEntity.ok(this.dashboardService.getProfessorsWithWeekEventsListFromDayAndCourse(weekday, course));
    }

    @Operation(summary = "Get classroom with events", description = "Retrieves a classroom and their events by classroom ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Classroom and events successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClassroomWithEventsDTO.class))),
        @ApiResponse(responseCode = "400", description = "Classroom ID was not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Classroom not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(CLASSROOMS + BY_ID)
    public ResponseEntity<ClassroomWithEventsDTO> getClassroomWithWeekEventsList(@PathVariable String id) {
        return ResponseEntity.ok(this.dashboardService.getClassroomWithEvents(id));
    }
    
    @Operation(summary = "List classrooms by floor", description = "Lists all classrooms filtered by floor.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of classrooms successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Classroom.class))),
        @ApiResponse(responseCode = "400", description = "Floor value was not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(CLASSROOMS)
    public ResponseEntity<List<Classroom>> getClassroomsFromFloor(@RequestParam String floor) {
        return ResponseEntity.ok(this.dashboardService.getClassroomsFromFloor(floor));
    }

    @Operation(summary = "List all courses", description = "Lists all available courses.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of courses successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Course.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(COURSES)
    public ResponseEntity<List<Course>> getAllCoursesList() {
        return ResponseEntity.ok(this.dashboardService.getAllCoursesList());
    }
    
    @Operation(summary = "Search professors and classrooms", description = "Searches for professors and classrooms by the provided term.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Search successful.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchQueryResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid query.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(SEARCH)
    public ResponseEntity<SearchQueryResponseDTO> searchProfessorsAndClassrooms(@RequestParam String query) {
        return ResponseEntity.ok(this.dashboardService.searchProfessorsAndClassrooms(query));
    }
    
}
