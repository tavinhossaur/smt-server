package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

@Service
public class DeleteProfessorUseCase implements UseCase<String, ApiResponse<Void>> {

    @Autowired
    private ProfessorRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        this.repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status("success")
                    .message("Professor deleted successfully."
                ).build()
            );
    }
    
}
