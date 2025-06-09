package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class UpdateProfessorUseCase implements UseCase<ProfessorDTO, Professor> {

    @Autowired
    private ProfessorRepository repository;

    @Override
    public ResponseEntity<Professor> execute(ProfessorDTO input, String id) {
        Professor existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));

        if (input.name() != null) existing.setName(input.name());
        if (input.email() != null) existing.setEmail(input.email());

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<Professor> execute(ProfessorDTO input) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
