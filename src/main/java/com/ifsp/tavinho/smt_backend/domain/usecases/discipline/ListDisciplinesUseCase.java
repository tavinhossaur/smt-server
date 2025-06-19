package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListDisciplinesUseCase implements UseCase<Void, List<Discipline>> {

    private final DisciplineRepository repository;

    @Override
    public List<Discipline> execute(Void v) {
        return this.repository.findAll();
    }
    
}
