package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteClassroomUseCase implements UseCase<String, ApiResponse<Void>> {

    private final ClassroomRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        Classroom classroom = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + id));

        this.repository.delete(classroom);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Classroom deleted successfully.")
                    .build()
            );
    }
    
}
