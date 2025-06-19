package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateProfessorUseCase implements UseCase<ProfessorDTO, Professor> {

    private final ProfessorRepository repository;

    @Override
    public Professor execute(ProfessorDTO input, Professor existing) {
        if (input.name() != null) existing.setName(input.name());
        if (input.email() != null) existing.setEmail(input.email());

        return this.repository.save(existing);
    }

    @Override
    public Professor execute(ProfessorDTO input) {
        throw new UnsupportedOperationException("An existing professor is required for update.");
    }
    
}
