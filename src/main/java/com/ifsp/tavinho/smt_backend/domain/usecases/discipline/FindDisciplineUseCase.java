package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class FindDisciplineUseCase implements UseCase<String, Discipline> {

    @Autowired
    private DisciplineRepository repository;

    @Override
    public ResponseEntity<Discipline> execute(String id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + id)));
    }
    
}
