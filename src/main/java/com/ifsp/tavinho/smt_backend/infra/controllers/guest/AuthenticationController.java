package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.application.dtos.input.LoginCredentialsDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.LoginResponseDTO;
import com.ifsp.tavinho.smt_backend.application.services.guest.AuthenticationService;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.BASE_API_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.LOGIN;

@Tag(name = "Authentication (Guest)", description = "Authentication endpoints for user login.")
@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_API_ROUTE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token if credentials are valid.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful. JWT token returned."),
        @ApiResponse(responseCode = "400", description = "Invalid credentials or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PostMapping(LOGIN)
    public ResponseEntity<ServerApiResponse<LoginResponseDTO>> login(@RequestBody @Valid LoginCredentialsDTO credentials) { 
        return ResponseEntity.ok(
            ServerApiResponse.<LoginResponseDTO>builder()
                .status(Status.SUCCESS)
                .message("User logged in successfully.")
                .data(this.authenticationService.login(credentials)
            ).build()
        );
    }
    
}
