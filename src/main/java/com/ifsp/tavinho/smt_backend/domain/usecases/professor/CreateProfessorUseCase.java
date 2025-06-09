package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class CreateProfessorUseCase implements UseCase<ProfessorDTO, Professor> {

    @Autowired
    private ProfessorRepository repository;

    @Override
    public ResponseEntity<Professor> execute(ProfessorDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(new Professor(input.name(), input.email())));
    }
    
}
