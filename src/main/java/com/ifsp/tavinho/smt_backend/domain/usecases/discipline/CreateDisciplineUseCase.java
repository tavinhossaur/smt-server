package com.ifsp.tavinho.smt_backend.domain.usecases.discipline;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateDisciplineUseCase implements UseCase<DisciplineDTO, Discipline> {

    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<Discipline> execute(DisciplineDTO input) {
        this.courseRepository.findById(input.courseId()).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + input.courseId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(
            this.disciplineRepository.save(
                new Discipline(
                    input.name(), 
                    input.abbreviation(), 
                    input.courseId()
                )
            )
        );
    }
    
}
