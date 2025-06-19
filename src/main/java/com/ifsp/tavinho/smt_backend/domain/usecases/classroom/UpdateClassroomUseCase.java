package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateClassroomUseCase implements UseCase<ClassroomDTO, Classroom> {

    private final ClassroomRepository repository;

    @Override
    public Classroom execute(ClassroomDTO input, Classroom existing) {
        if (input.description() != null) existing.setDescription(input.description());
        if (input.block() != null) existing.setBlock(input.block());
        if (input.floor() != null) existing.setFloor(input.floor());
        if (input.capacity() != null) existing.setCapacity(input.capacity());
        if (input.observation() != null) existing.setObservation(input.observation());

        return this.repository.save(existing);
    }

    @Override
    public Classroom execute(ClassroomDTO input) {
        throw new UnsupportedOperationException("An existing classroom is required for update.");
    }
    
}
