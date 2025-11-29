package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.input.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.application.services.admin.DisciplineService;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
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

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_DISCIPLINES;

@Tag(name = "Disciplines (Admin)", description = "Discipline management. Requires administrator permission.")
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_DISCIPLINES)
public class DisciplineController implements EntityController<DisciplineDTO, Discipline> {
    
    private final DisciplineService disciplineService;

    @Override
    @Operation(summary = "Create discipline", description = "Creates a new discipline with the provided data.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Discipline successfully created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Discipline.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Discipline> create(@Valid DisciplineDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.disciplineService.create(input));
    }

    @Override
    @Operation(summary = "Update discipline", description = "Updates an existing discipline by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Discipline successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Discipline.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Discipline not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Discipline> update(DisciplineDTO input, String id) {
        return ResponseEntity.ok(this.disciplineService.update(input, id));
    }

    @Override
    @Operation(summary = "Delete discipline", description = "Deletes a discipline by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Discipline successfully deleted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Discipline not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "409", description = "Discipline could not be deleted because it is linked to an event.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        this.disciplineService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Discipline deleted successfully.")
                    .build()
            );
    }

    @Override
    @Operation(summary = "Find discipline", description = "Finds a discipline by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Discipline found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Discipline.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Discipline not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Discipline> find(String id) {
        return ResponseEntity.ok(this.disciplineService.find(id));
    }

    @Override
    @Operation(summary = "List disciplines", description = "Lists all registered disciplines.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of disciplines successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Discipline.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<List<Discipline>> list() {
        return ResponseEntity.ok(this.disciplineService.list());
    }

}
