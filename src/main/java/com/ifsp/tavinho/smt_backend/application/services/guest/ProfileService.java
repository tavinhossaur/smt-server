package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.usecases.user.FindUserUseCase;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final FindUserUseCase findUser;
    private final ListFavoritesUseCase listFavorites;
    private final UpdateFavoritesUseCase updateFavorites;
    private final UpdatePasswordUseCase updatePassword;
    private final UpdateProfilePhotoUseCase updateProfilePhoto;

    private final ProfessorRepository professorRepository;

    public User findCurrentUser() {
        return this.findUser.execute(getAuthenticatedUserId());
    }

    public List<Favorite> listFavorites() {
        User user = this.findUser.execute(getAuthenticatedUserId());
        return this.listFavorites.execute(user.getId());
    }

    public Boolean updateFavorites(UpdateFavoritesDTO input) {
        User user = this.findUser.execute(getAuthenticatedUserId());

        String professorId = input.professorId();

        if (professorId.isBlank() || professorId == null) {
            throw new AppError("Professor id must be provided.", HttpStatus.BAD_REQUEST);
        }

        if (!this.professorRepository.existsById(professorId)) throw new EntityNotFoundException("Professor not found with id: " + professorId);

        return this.updateFavorites.execute(professorId, user.getId());
    }

    public Boolean updatePassword(UpdatePasswordDTO input) {
        User user = this.findUser.execute(getAuthenticatedUserId());
        
        if (input.currentPassword().isBlank() || input.currentPassword() == null || input.newPassword().isBlank() || input.newPassword() == null) {
            throw new AppError("Current and new passwords must be provided.", HttpStatus.BAD_REQUEST);
        }

        return this.updatePassword.execute(input, user) != null;
    }

    public Boolean updateProfilePhoto(UpdateProfilePhotoDTO input) {
        User user = this.findUser.execute(getAuthenticatedUserId());

        String encodedBase64Image = input.encodedBase64Image();

        if (encodedBase64Image.isBlank() || encodedBase64Image == null) {
            throw new AppError("Photo must be provided.", HttpStatus.BAD_REQUEST);
        }

        return this.updateProfilePhoto.execute(encodedBase64Image, user) != null;
    }

    private String getAuthenticatedUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
