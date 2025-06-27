package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.services.admin.UserService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.UserDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
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

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_USERS;

@Tag(name = "Users (Admin)", description = "User management. Requires administrator permission.")
@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_USERS)
public class UserController implements EntityController<UserDTO, User> {
    
    private final UserService userService;

    @Override
    @Operation(summary = "Create user", description = "Creates a new user with the provided data.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User successfully created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<User> create(@Valid UserDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.create(input));
    }

    @Override
    @Operation(summary = "Update user", description = "Updates an existing user by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<User> update(UserDTO input, String id) {
        return ResponseEntity.ok(this.userService.update(input, id));
    }

    @Override
    @Operation(summary = "Delete user", description = "Deletes a user by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User successfully deleted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ServerApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("User deleted successfully.")
                    .build()
            );
    }

    @Override
    @Operation(summary = "Find user", description = "Finds a user by ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<User> find(String id) {
        return ResponseEntity.ok(this.userService.find(id));
    }

    @Override
    @Operation(summary = "List users", description = "Lists all registered users.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(this.userService.list());
    }

}
