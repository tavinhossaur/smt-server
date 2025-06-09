package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindProfessorWithEventsUseCase implements UseCase<String, ProfessorWithEventsDTO> {

    private final ProfessorRepository professorRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<ProfessorWithEventsDTO> execute(String id) {
        Professor professor = this.professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
        List<Event> professorEvents = this.eventRepository.findByProfessorId(id);

        return ResponseEntity.ok(new ProfessorWithEventsDTO(professor, professorEvents));
    }
    
}