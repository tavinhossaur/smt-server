package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindProfessorWithEventsUseCase implements UseCase<Professor, ProfessorWithEventsDTO> {

    private final EventRepository eventRepository;

    @Override
    public ProfessorWithEventsDTO execute(Professor professor) {
        List<Event> professorEvents = this.eventRepository.findByProfessorId(professor.getId());

        return new ProfessorWithEventsDTO(
            professor.getId(),
            professor.getName(),
            professor.getEmail(),
            professor.getCreatedAt(),
            professor.getUpdatedAt(),
            professorEvents
        );
    }
    
}