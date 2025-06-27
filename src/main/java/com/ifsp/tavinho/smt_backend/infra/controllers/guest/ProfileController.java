package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;
import com.ifsp.tavinho.smt_backend.application.services.guest.ProfileService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfilePhotoResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PASSWORD;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.FAVORITES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PHOTO;

@Tag(name = "Profile (Guest)", description = "Endpoints for managing the authenticated user's profile.")
@RestController
@RequiredArgsConstructor
@RequestMapping(PROFILE_ROUTE)
public class ProfileController {
    
    private final ProfileService profileService;

    @Operation(summary = "Get current user", description = "Retrieves the currently authenticated user's profile.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User profile successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<User> findCurrentUser() {
        return ResponseEntity.ok(this.profileService.findCurrentUser());
    }

    @Operation(summary = "Get current user profile photo", description = "Retrieves the currently authenticated user's profile photo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User profile photo successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfilePhotoResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(PHOTO)
    public ResponseEntity<ProfilePhotoResponseDTO> findCurrentUserProfilePhoto() {
        return ResponseEntity.ok(this.profileService.findCurrentUserProfilePhoto());
    }

    @Operation(summary = "List favorites", description = "Lists all favorites of the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorites successfully returned.", content = @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = Favorite.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(FAVORITES)
    public ResponseEntity<List<Favorite>> listFavorites() {
        return ResponseEntity.ok(this.profileService.listFavorites()); 
    }

    @Operation(summary = "Update favorites", description = "Updates the list of favorites for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorites updated successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(FAVORITES)
    public ResponseEntity<ServerApiResponse<Boolean>> updateFavorites(@RequestBody @Valid UpdateFavoritesDTO input) {
        return ResponseEntity.ok(
            ServerApiResponse.<Boolean>builder()
                .status(Status.SUCCESS)
                .message("Favorites updated successfully.")
                .data(this.profileService.updateFavorites(input))
            .build()
        );
    }

    @Operation(summary = "Update password", description = "Updates the password for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Password updated successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(PASSWORD)
    public ResponseEntity<ServerApiResponse<Boolean>> updatePassword(@RequestBody @Valid UpdatePasswordDTO input) { 
        return ResponseEntity.ok(
            ServerApiResponse.<Boolean>builder()
                .status(Status.SUCCESS)
                .message("Password updated successfully.")
                .data(this.profileService.updatePassword(input))
            .build()
        );
    }

    @Operation(summary = "Update profile photo", description = "Updates the profile photo for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile photo updated successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(PHOTO)
    public ResponseEntity<ServerApiResponse<Boolean>> updateProfilePhoto(@RequestBody @Valid UpdateProfilePhotoDTO input) { 
        return ResponseEntity.ok(
            ServerApiResponse.<Boolean>builder()
                .status(Status.SUCCESS)
                .message("Profile photo updated successfully.")
                .data(this.profileService.updateProfilePhoto(input))
            .build()
        );
    }

}
