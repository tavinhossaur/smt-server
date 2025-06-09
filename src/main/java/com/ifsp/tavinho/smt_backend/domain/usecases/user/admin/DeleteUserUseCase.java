package com.ifsp.tavinho.smt_backend.domain.usecases.user.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.repositories.UserRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

@Service
public class DeleteUserUseCase implements UseCase<String, ApiResponse<Void>> {

    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        this.repository.deleteById(id);
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .status("success")
                .message("User deleted successfully."
            ).build()
        );
    }
    
}
