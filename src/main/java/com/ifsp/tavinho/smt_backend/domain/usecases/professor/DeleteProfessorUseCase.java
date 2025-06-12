package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteProfessorUseCase implements UseCase<String, ApiResponse<Void>> {

    private final ProfessorRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        Professor professor = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));

        this.repository.delete(professor);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Professor deleted successfully.")
                    .build()
            );
    }
    
}
