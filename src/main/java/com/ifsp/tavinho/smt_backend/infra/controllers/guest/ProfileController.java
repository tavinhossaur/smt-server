package com.ifsp.tavinho.smt_backend.infra.controllers.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.ListFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdateFavoritesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdatePasswordUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.profile.UpdateProfilePhotoUseCase;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_ROUTE;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_PASSWORD;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_FAVORITES;
import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.PROFILE_PHOTO;

@RestController
@AllArgsConstructor
@RequestMapping(PROFILE_ROUTE)
public class ProfileController {
    
    private final ListFavoritesUseCase listFavorites;
    private final UpdateFavoritesUseCase updateFavorites;
    private final UpdatePasswordUseCase updatePassword;
    private final UpdateProfilePhotoUseCase updateProfilePhoto;

    @GetMapping(PROFILE_FAVORITES)
    public ResponseEntity<List<Favorite>> listFavorites(@PathVariable String id) { 
        return listFavorites.execute(id); 
    }

    @PutMapping(PROFILE_FAVORITES)
    public ResponseEntity<ApiResponse<Void>> updateFavorites(@RequestBody UpdateFavoritesDTO input, @PathVariable String id) { 
        return updateFavorites.execute(input, id); 
    }

    @PatchMapping(PROFILE_PASSWORD)
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody UpdatePasswordDTO input, @PathVariable String id) { 
        return updatePassword.execute(input, id); 
    }

    @PatchMapping(PROFILE_PHOTO)
    public ResponseEntity<ApiResponse<Void>> updateProfilePhoto(@RequestBody UpdateProfilePhotoDTO input, @PathVariable String id) { 
        return updateProfilePhoto.execute(input, id); 
    }

}
