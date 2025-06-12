package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateClassroomUseCase implements UseCase<ClassroomDTO, Classroom> {

    private final ClassroomRepository repository;

    @Override
    public ResponseEntity<Classroom> execute(ClassroomDTO input, String id) {
        Classroom existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + id));

        if (input.description() != null) existing.setDescription(input.description());
        if (input.block() != null) existing.setBlock(input.block());
        if (input.floor() != null) existing.setFloor(input.floor());
        if (input.capacity() != null) existing.setCapacity(input.capacity());
        if (input.observation() != null) existing.setObservation(input.observation());

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<Classroom> execute(ClassroomDTO input) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
