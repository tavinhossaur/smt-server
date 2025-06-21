package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
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
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BY_ID;

@Tag(name = "Dashboard (Guest)", description = "Endpoints for dashboard data access, such as professors, classrooms, and courses.")
@RestController
@RequiredArgsConstructor
@RequestMapping(DASHBOARD_ROUTE)
public class DashboardController {
    
    private final DashboardService dashboardService;

    @Operation(summary = "Get professor with events", description = "Retrieves a professor and their events by professor ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor and events successfully returned."),
        @ApiResponse(responseCode = "400", description = "Professor ID was not provided.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Professor not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(PROFESSORS + BY_ID)
    public ResponseEntity<ProfessorWithEventsDTO> getProfessorWithWeekEventsList(@PathVariable String id) {
        return ResponseEntity.ok(this.dashboardService.getProfessorWithWeekEventsList(id));
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
    
    @Operation(summary = "List classrooms by floor", description = "Lists all classrooms filtered by floor.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of classrooms successfully returned."),
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
        @ApiResponse(responseCode = "200", description = "List of courses successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(COURSES)
    public ResponseEntity<List<Course>> getAllCoursesList() {
        return ResponseEntity.ok(this.dashboardService.getAllCoursesList());
    }
    
}
