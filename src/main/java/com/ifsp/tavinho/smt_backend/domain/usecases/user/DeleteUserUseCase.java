package com.ifsp.tavinho.smt_backend.domain.usecases.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.User;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements UseCase<String, ApiResponse<Void>> {

    private final UserRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        User user = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        this.repository.delete(user);
        
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .message("User deleted successfully."
            ).build()
        );
    }
    
}
