package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class FindProfessorUseCase implements UseCase<String, Professor> {

    @Autowired
    private ProfessorRepository repository;

    @Override
    public ResponseEntity<Professor> execute(String id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id)));
    }
    
}
