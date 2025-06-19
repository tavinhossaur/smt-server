package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateFavoritesDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.FavoriteRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateFavoritesUseCase implements UseCase<UpdateFavoritesDTO, ApiResponse<Void>> {

    private final UserRepository userRepository;
    private final ProfessorRepository professorRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdateFavoritesDTO input, String userId) {
        String professorId = input.professorId();

        if (professorId.isBlank() || professorId == null) {
            throw new AppError("Professor id must be provided.", HttpStatus.BAD_REQUEST);
        }

        if (!this.userRepository.existsById(userId)) throw new EntityNotFoundException("User not found with id: " + userId);
        if (!this.professorRepository.existsById(professorId)) throw new EntityNotFoundException("Professor not found with id: " + professorId);

        Optional<Favorite> favorite = this.favoriteRepository.findByUserIdAndProfessorId(userId, professorId);

        if (favorite.isPresent()) this.favoriteRepository.delete(favorite.get());
        else this.favoriteRepository.save(new Favorite(userId, professorId));

        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .message("User favorites updated successfully.")
            .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdateFavoritesDTO _unused) {
        throw new UnsupportedOperationException("User ID is required for updating the favorites.");
    }
    
}
