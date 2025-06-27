package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.services.admin.ProfessorService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import org.springframework.http.HttpStatus;
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

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_PROFESSORS;

@Tag(name = "Professors (Admin)", description = "Professor management. Requires administrator permission.")
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_PROFESSORS)
public class ProfessorController implements EntityController<ProfessorDTO, Professor> {
    
    private final ProfessorService professorService;

    @Override
    @Operation(summary = "Create professor", description = "Creates a new professor with the provided data.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Professor successfully created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Professor> create(@Valid ProfessorDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.professorService.create(input));
    }

    @Override
    @Operation(summary = "Update professor", description = "Updates an existing professor by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Professor not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Professor> update(ProfessorDTO input, String id) {
        return ResponseEntity.ok(this.professorService.update(input, id));
    }

    @Override
    @Operation(summary = "Delete professor", description = "Deletes a professor by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor successfully deleted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Professor not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "409", description = "Professor could not be deleted because it is linked to an event.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        this.professorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Professor deleted successfully.")
                    .build()
            );
    }

    @Override
    @Operation(summary = "Find professor", description = "Finds a professor by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Professor not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Professor> find(String id) {
        return ResponseEntity.ok(this.professorService.find(id));
    }

    @Override
    @Operation(summary = "List professors", description = "Lists all registered professors.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of professors successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Professor.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<List<Professor>> list() {
        return ResponseEntity.ok(this.professorService.list());
    }

}
