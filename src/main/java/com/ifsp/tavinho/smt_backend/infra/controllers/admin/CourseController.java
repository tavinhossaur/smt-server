package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.services.admin.CourseService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.CourseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_COURSES;

@Tag(name = "Courses (Admin)", description = "Course management. Requires administrator permission.")
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_COURSES)
public class CourseController implements EntityController<CourseDTO, Course> {

    private final CourseService courseService;

    @Override
    @Operation(summary = "Create course", description = "Creates a new course with the provided data.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Course successfully created."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Course> create(@Valid CourseDTO input) {
        return this.courseService.create(input);
    }

    @Override
    @Operation(summary = "Update course", description = "Updates an existing course by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course successfully updated."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Course> update(CourseDTO input, String id) {
        return this.courseService.update(input, id);
    }

    @Override
    @Operation(summary = "Delete course", description = "Deletes a course by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course successfully deleted."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.courseService.delete(id);
    }

    @Override
    @Operation(summary = "Find course", description = "Finds a course by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Course found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Course not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Course> find(String id) {
        return this.courseService.find(id);
    }

    @Override
    @Operation(summary = "List courses", description = "Lists all registered courses.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of courses successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<List<Course>> list() {
        return this.courseService.list();
    }
    
}
