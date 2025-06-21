package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProfessorsUseCase implements UseCase<Void, List<Professor>> {

    private final ProfessorRepository repository;

    @Override
    public List<Professor> execute(Void v) {
        return this.repository.findAllByOrderByNameAsc();
    }
    
}
