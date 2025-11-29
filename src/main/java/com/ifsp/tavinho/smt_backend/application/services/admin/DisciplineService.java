package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final EventRepository eventRepository;

    public Discipline create(DisciplineDTO input) {
        this.courseRepository.findById(input.courseId()).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + input.courseId()));
        return this.disciplineRepository.save(
            new Discipline(
                input.name(), 
                input.abbreviation(), 
                input.courseId()
            )
        );
    }

    public Discipline update(DisciplineDTO input, String id) {
        Discipline existing = this.find(id);

        if (input.name() != null) existing.setName(input.name());
        if (input.abbreviation() != null) existing.setAbbreviation(input.abbreviation());
        if (input.courseId() != null) existing.setCourseId(input.courseId());

        return this.disciplineRepository.save(existing);
    }

    public Boolean delete(String id) {
        Discipline discipline = this.find(id);

        if (this.eventRepository.existsByDisciplineId(discipline.getId())) {
            throw new AppError("Discipline could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        this.disciplineRepository.delete(discipline);
        return true;
    }

    public Discipline find(String id) {
        return this.disciplineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + id));
    }

    public List<Discipline> list() {
        return this.disciplineRepository.findAllByOrderByNameAsc();
    }
    
}
