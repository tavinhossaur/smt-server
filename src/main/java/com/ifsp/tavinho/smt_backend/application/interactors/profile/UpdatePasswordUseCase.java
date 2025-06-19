package com.ifsp.tavinho.smt_backend.application.interactors.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.UpdatePasswordDTO;
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
public class UpdatePasswordUseCase implements UseCase<UpdatePasswordDTO, ApiResponse<Void>> {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdatePasswordDTO input, String userId) {
        String currentPassword = input.currentPassword();
        String newPassword = input.newPassword();

        if (currentPassword.isBlank() || currentPassword == null || newPassword.isBlank() || newPassword == null) {
            throw new AppError("Current and new passwords must be provided.", HttpStatus.BAD_REQUEST);
        }

        User user = this.repository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        if (!this.passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new AppError("Current password is incorrect.", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));

        this.repository.save(user);

        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .message("User password updated successfully.")
            .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(UpdatePasswordDTO _unused) {
        throw new UnsupportedOperationException("ID is required for updating the password.");
    }

}
