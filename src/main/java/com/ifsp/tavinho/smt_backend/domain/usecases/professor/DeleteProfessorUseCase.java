package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteProfessorUseCase implements UseCase<Professor, Boolean> {

    private final ProfessorRepository repository;

    @Override
    public Boolean execute(Professor professor) {
        this.repository.delete(professor);
        return true;
    }
    
}
