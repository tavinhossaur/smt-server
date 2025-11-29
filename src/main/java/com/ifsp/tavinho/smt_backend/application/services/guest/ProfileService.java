package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ifsp.tavinho.smt_backend.application.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.input.UpdatePasswordDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.ProfilePhotoResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.repositories.FavoriteRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final PasswordEncoder passwordEncoder;
    
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final ProfessorRepository professorRepository;

    public User findCurrentUser() {
        return this.userRepository.findById(getAuthenticatedUserId()).orElseThrow(() -> new EntityNotFoundException("Current user could not be found"));
    }

    public ProfilePhotoResponseDTO findCurrentUserProfilePhoto() {
        return new ProfilePhotoResponseDTO(this.findCurrentUser().getProfilePhoto());
    }

    public List<Favorite> listFavorites() {
        return this.favoriteRepository.findAllByUserId(getAuthenticatedUserId());
    }

    public Boolean updateFavorites(UpdateFavoritesDTO input) {
        String professorId = input.professorId();

        if (professorId.isBlank() || professorId == null) {
            throw new AppError("Professor id must be provided.", HttpStatus.BAD_REQUEST);
        }

        if (!this.professorRepository.existsById(professorId)) throw new EntityNotFoundException("Professor not found with id: " + professorId);

        Optional<Favorite> favorite = this.favoriteRepository.findByUserIdAndProfessorId(getAuthenticatedUserId(), professorId);

        if (favorite.isPresent()) this.favoriteRepository.delete(favorite.get());
        else this.favoriteRepository.save(new Favorite(getAuthenticatedUserId(), professorId));

        return true;
    }

    public Boolean updatePassword(UpdatePasswordDTO input) {
        User user = this.findCurrentUser();
        
        if (input.currentPassword().isBlank() || input.currentPassword() == null || input.newPassword().isBlank() || input.newPassword() == null) {
            throw new AppError("Current and new passwords must be provided.", HttpStatus.BAD_REQUEST);
        }

        String currentPassword = input.currentPassword();
        String newPassword = input.newPassword();

        if (!this.passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AppError("Current password is incorrect.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));

        this.userRepository.save(user);

        return true;
    }

    public Boolean updateProfilePhoto(UpdateProfilePhotoDTO input) {
        User user = this.findCurrentUser();

        String encodedBase64Image = input.encodedBase64Image();

        if (encodedBase64Image.isBlank() || encodedBase64Image == null) {
            throw new AppError("Photo must be provided.", HttpStatus.BAD_REQUEST);
        }

        user.setProfilePhoto(encodedBase64Image);

        this.userRepository.save(user);

        return true;
    }

    private String getAuthenticatedUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
