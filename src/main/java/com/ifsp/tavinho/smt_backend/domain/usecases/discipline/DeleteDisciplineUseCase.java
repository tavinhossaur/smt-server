package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteDisciplineUseCase implements UseCase<Discipline, Boolean> {

    private final DisciplineRepository repository;

    @Override
    public Boolean execute(Discipline discipline) {
        this.repository.delete(discipline);
        return true;
    }
    
}
