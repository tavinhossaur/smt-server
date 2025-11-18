package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProfessorsWithEventsUseCase implements UseCase<Weekday, List<ProfessorWithEventsDTO>> {

    private final ProfessorRepository professorRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ProfessorWithEventsDTO> execute(Weekday weekday, String courseId) {
        List<Event> eventsList = this.eventRepository.findByWeekdayAndCourseId(weekday.name(), courseId);
    
        Map<String, List<Event>> eventsByProfessor = eventsList.stream().collect(Collectors.groupingBy(Event::getProfessorId));
    
        List<ProfessorWithEventsDTO> professorsWithEventsList = new ArrayList<>();
    
        for (Map.Entry<String, List<Event>> entry : eventsByProfessor.entrySet()) {
            String professorId = entry.getKey();
            List<Event> professorEvents = entry.getValue();
    
            Optional<Professor> professor = this.professorRepository.findById(professorId);
            
            if (professor.isEmpty()) continue;
    
            professorsWithEventsList.add(
                new ProfessorWithEventsDTO(
                    professor.get().getId(),
                    professor.get().getName(),
                    professor.get().getEmail(),
                    professor.get().getCreatedAt(),
                    professor.get().getUpdatedAt(),
                    professorEvents
                )
            );
        }
    
        return professorsWithEventsList;
    }

    @Override
    public List<ProfessorWithEventsDTO> execute(Weekday _unused) {
        throw new UnsupportedOperationException("Course ID is required for this query.");
    }
    
}