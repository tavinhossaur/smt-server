package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

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
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final FindUserUseCase findUser;
    private final ListFavoritesUseCase listFavorites;
    private final UpdateFavoritesUseCase updateFavorites;
    private final UpdatePasswordUseCase updatePassword;
    private final UpdateProfilePhotoUseCase updateProfilePhoto;

    public ResponseEntity<User> findCurrentUser() {
        return this.findUser.execute(getAuthenticatedUserId());
    }

    public ResponseEntity<List<Favorite>> listFavorites() {
        return this.listFavorites.execute(getAuthenticatedUserId());
    }

    public ResponseEntity<ServerApiResponse<Void>> updateFavorites(UpdateFavoritesDTO input) {
        return this.updateFavorites.execute(input, getAuthenticatedUserId());
    }

    public ResponseEntity<ServerApiResponse<Void>> updatePassword(UpdatePasswordDTO input) {
        return this.updatePassword.execute(input, getAuthenticatedUserId());
    }

    public ResponseEntity<ServerApiResponse<Void>> updateProfilePhoto(UpdateProfilePhotoDTO input) {
        return this.updateProfilePhoto.execute(input, getAuthenticatedUserId());
    }

    private String getAuthenticatedUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
