package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindProfessorUseCase implements UseCase<String, Professor> {

    private final ProfessorRepository repository;

    @Override
    public Professor execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
    }
    
}
