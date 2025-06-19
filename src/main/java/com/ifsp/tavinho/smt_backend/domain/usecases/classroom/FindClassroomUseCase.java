package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindClassroomUseCase implements UseCase<String, Classroom> {

    private final ClassroomRepository repository;

    @Override
    public Classroom execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + id));
    }
    
}
