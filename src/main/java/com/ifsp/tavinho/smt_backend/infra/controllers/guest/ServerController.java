package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.HealthCheckResponseDTO;
import com.ifsp.tavinho.smt_backend.application.services.guest.ServerService;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.HEALTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(BASE_API_ROUTE)
@Tag(name = "Server (Guest)", description = "Server endpoints for health check.")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @Operation(summary = "Health check", description = "Checks if the server is running.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Server is running.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HealthCheckResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(HEALTH)
    public ResponseEntity<ServerApiResponse<HealthCheckResponseDTO>> health() {
        return ResponseEntity.ok(
            ServerApiResponse.<HealthCheckResponseDTO>builder()
                .status(Status.SUCCESS)
                .message("Server is running.")
                .data(this.serverService.healthCheck())
            .build()
        );
    }
}
