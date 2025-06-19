package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;
import com.ifsp.tavinho.smt_backend.application.interactors.profile.ListFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.profile.UpdateFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.profile.UpdatePasswordUseCase;
import com.ifsp.tavinho.smt_backend.application.interactors.profile.UpdateProfilePhotoUseCase;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.FindUserUseCase;

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
    
    private final FindUserUseCase findUser;
    private final ListFavoritesUseCase listFavorites;
    private final UpdateFavoritesUseCase updateFavorites;
    private final UpdatePasswordUseCase updatePassword;
    private final UpdateProfilePhotoUseCase updateProfilePhoto;

    @Operation(summary = "Get current user", description = "Retrieves the currently authenticated user's profile.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User profile successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<User> findCurrentUser() {
        return this.findUser.execute(getAuthenticatedUserId());
    }

    @Operation(summary = "List favorites", description = "Lists all favorites of the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorites successfully returned."),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @GetMapping(FAVORITES)
    public ResponseEntity<List<Favorite>> listFavorites() {
        return this.listFavorites.execute(getAuthenticatedUserId()); 
    }

    @Operation(summary = "Update favorites", description = "Updates the list of favorites for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorites successfully updated."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(FAVORITES)
    public ResponseEntity<ServerApiResponse<Void>> updateFavorites(@RequestBody @Valid UpdateFavoritesDTO input) { 
        return this.updateFavorites.execute(input, getAuthenticatedUserId()); 
    }

    @Operation(summary = "Update password", description = "Updates the password for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Password successfully updated."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(PASSWORD)
    public ResponseEntity<ServerApiResponse<Void>> updatePassword(@RequestBody @Valid UpdatePasswordDTO input) { 
        return this.updatePassword.execute(input, getAuthenticatedUserId()); 
    }

    @Operation(summary = "Update profile photo", description = "Updates the profile photo for the authenticated user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile photo successfully updated."),
        @ApiResponse(responseCode = "400", description = "Invalid data or validation error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerApiResponse.class)))
    })
    @PatchMapping(PHOTO)
    public ResponseEntity<ServerApiResponse<Void>> updateProfilePhoto(@RequestBody @Valid UpdateProfilePhotoDTO input) { 
        return this.updateProfilePhoto.execute(input, getAuthenticatedUserId()); 
    }

    private String getAuthenticatedUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
