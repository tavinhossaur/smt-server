package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteDisciplineUseCase implements UseCase<String, ApiResponse<Void>> {

    private final DisciplineRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        Discipline discipline = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + id));

        this.repository.delete(discipline);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Discipline deleted successfully.")
                    .build()
            );
    }
    
}
