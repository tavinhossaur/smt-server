package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.admin.FindUserUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.ListFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdateFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdatePasswordUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdateProfilePhotoUseCase;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PASSWORD;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.FAVORITES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PHOTO;

@RestController
@AllArgsConstructor
@RequestMapping(PROFILE_ROUTE)
public class ProfileController {
    
    private final FindUserUseCase findUser;
    private final ListFavoritesUseCase listFavorites;
    private final UpdateFavoritesUseCase updateFavorites;
    private final UpdatePasswordUseCase updatePassword;
    private final UpdateProfilePhotoUseCase updateProfilePhoto;

    @GetMapping
    public ResponseEntity<User> findCurrentUser() {
        return this.findUser.execute(getAuthenticatedUserId());
    }

    @GetMapping(FAVORITES)
    public ResponseEntity<List<Favorite>> listFavorites() {
        return this.listFavorites.execute(getAuthenticatedUserId()); 
    }

    @PatchMapping(FAVORITES)
    public ResponseEntity<ApiResponse<Void>> updateFavorites(@RequestBody @Valid UpdateFavoritesDTO input) { 
        return this.updateFavorites.execute(input, getAuthenticatedUserId()); 
    }

    @PatchMapping(PASSWORD)
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody @Valid UpdatePasswordDTO input) { 
        return this.updatePassword.execute(input, getAuthenticatedUserId()); 
    }

    @PatchMapping(PHOTO)
    public ResponseEntity<ApiResponse<Void>> updateProfilePhoto(@RequestBody @Valid UpdateProfilePhotoDTO input) { 
        return this.updateProfilePhoto.execute(input, getAuthenticatedUserId()); 
    }

    private String getAuthenticatedUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
