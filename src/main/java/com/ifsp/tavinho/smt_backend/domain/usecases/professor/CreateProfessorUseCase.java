package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateProfessorUseCase implements UseCase<ProfessorDTO, Professor> {

    private final ProfessorRepository repository;

    @Override
    public Professor execute(ProfessorDTO input) {
        return this.repository.save(new Professor(input.name(), input.email()));
    }
    
}
