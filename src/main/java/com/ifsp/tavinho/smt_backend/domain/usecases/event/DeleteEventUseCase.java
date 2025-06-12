package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteEventUseCase implements UseCase<String, ApiResponse<Void>> {

    private final EventRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        Event event = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        this.repository.delete(event);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Event deleted successfully.")
                    .build()
            );
    }
    
}
