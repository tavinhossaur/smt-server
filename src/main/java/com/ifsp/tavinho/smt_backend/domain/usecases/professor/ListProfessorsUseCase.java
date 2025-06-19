package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Professor>> execute(Void v) {
        return ResponseEntity.ok(this.repository.findAll());
    }
    
}
