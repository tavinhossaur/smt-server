package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindDisciplineUseCase implements UseCase<String, Discipline> {

    private final DisciplineRepository repository;

    @Override
    public Discipline execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + id));
    }
    
}
