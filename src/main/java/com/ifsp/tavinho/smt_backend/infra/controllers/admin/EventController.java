package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.input.EventDTO;
import com.ifsp.tavinho.smt_backend.application.services.admin.EventService;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
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

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_EVENTS;

@Tag(name = "Events (Admin)", description = "Event management. Requires administrator permission.")
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_EVENTS)
public class EventController implements EntityController<EventDTO, Event> {
    
    private final EventService eventService;

    @Override
    @Operation(summary = "Create event", description = "Creates a new event with the provided data.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Event successfully created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Event> create(@Valid EventDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.eventService.create(input));
    }

    @Override
    @Operation(summary = "Update event", description = "Updates an existing event by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Event> update(EventDTO input, String id) {
        return ResponseEntity.ok(this.eventService.update(input, id));
    }

    @Override
    @Operation(summary = "Delete event", description = "Deletes an event by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event successfully deleted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        this.eventService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Event deleted successfully.")
                    .build()
            );
    }

    @Override
    @Operation(summary = "Find event", description = "Finds an event by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Event found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Event not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<Event> find(String id) {
        return ResponseEntity.ok(this.eventService.find(id));
    }

    @Override
    @Operation(summary = "List events", description = "Lists all registered events.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of events successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Event.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<List<Event>> list() {
        return ResponseEntity.ok(this.eventService.list());
    }

}
