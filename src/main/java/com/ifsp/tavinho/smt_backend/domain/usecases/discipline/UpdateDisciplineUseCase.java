package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateDisciplineUseCase implements UseCase<DisciplineDTO, Discipline> {

    private final DisciplineRepository repository;

    @Override
    public ResponseEntity<Discipline> execute(DisciplineDTO input, String id) {
        Discipline existing = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + id));

        if (input.name() != null) existing.setName(input.name());
        if (input.abbreviation() != null) existing.setAbbreviation(input.abbreviation());
        if (input.courseId() != null) existing.setCourseId(input.courseId());

        return ResponseEntity.ok(this.repository.save(existing));
    }

    @Override
    public ResponseEntity<Discipline> execute(DisciplineDTO input) {
        throw new UnsupportedOperationException("ID is required for update.");
    }
    
}
