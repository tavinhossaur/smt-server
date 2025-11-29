package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final EventRepository eventRepository;

    public Professor create(ProfessorDTO input) {
        return this.professorRepository.save(new Professor(input.name(), input.email()));
    }

    public Professor update(ProfessorDTO input, String id) {
        Professor existing = this.find(id);

        if (input.name() != null) existing.setName(input.name());
        if (input.email() != null) existing.setEmail(input.email());

        return this.professorRepository.save(existing);
    }

    public Boolean delete(String id) {
        Professor professor = this.find(id);

        if (this.eventRepository.existsByProfessorId(professor.getId())) {
            throw new AppError("Professor could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        this.professorRepository.delete(professor);
        return true;
    }

    public Professor find(String id) {
        return this.professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
    }

    public List<Professor> list() {
        return this.professorRepository.findAllByOrderByNameAsc();
    }
    
}
