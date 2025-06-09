package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class CreateDisciplineUseCase implements UseCase<DisciplineDTO, Discipline> {

    @Autowired
    private DisciplineRepository repository;

    @Override
    public ResponseEntity<Discipline> execute(DisciplineDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(new Discipline(input.name(), input.courseId())));
    }
    
}
