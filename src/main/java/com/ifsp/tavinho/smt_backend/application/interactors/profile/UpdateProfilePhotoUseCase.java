package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdateProfilePhotoDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProfilePhotoUseCase implements UseCase<UpdateProfilePhotoDTO, ApiResponse<Void>> {

    private final UserRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdateProfilePhotoDTO input, String userId) {
        String encodedBase64Image = input.encodedBase64Image();

        if (encodedBase64Image.isBlank() || encodedBase64Image == null) {
            throw new AppError("Photo must be provided.", HttpStatus.BAD_REQUEST);
        }

        User user = this.repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.setProfilePhoto(encodedBase64Image);

        this.repository.save(user);

        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .message("Profile photo updated successfully.")
            .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdateProfilePhotoDTO _unused) {
        throw new UnsupportedOperationException("ID is required for updating the profile photo.");
    }
    
}
