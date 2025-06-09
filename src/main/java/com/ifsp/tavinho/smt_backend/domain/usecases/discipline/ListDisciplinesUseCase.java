package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class ListDisciplinesUseCase implements UseCase<Void, List<Discipline>> {

    @Autowired
    private DisciplineRepository repository;

    @Override
    public ResponseEntity<List<Discipline>> execute(Void v) {
        return ResponseEntity.ok(this.repository.findAll());
    }
    
}
