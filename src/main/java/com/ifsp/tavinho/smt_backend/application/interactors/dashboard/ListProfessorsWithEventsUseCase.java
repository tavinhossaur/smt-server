package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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
        List<ProfessorWithEventsDTO> professorsWithEventsList = new ArrayList<>();

        List<Event> eventsList = this.eventRepository.findByWeekdayAndCourseId(weekday.name(), courseId);

        for (Event event : eventsList) {
            Optional<Professor> professor = this.professorRepository.findById(event.getProfessorId());

            if (!professor.isPresent()) continue;

            professorsWithEventsList.add(
                new ProfessorWithEventsDTO(
                    professor.get().getId(),
                    professor.get().getName(),
                    professor.get().getEmail(),
                    professor.get().getCreatedAt(),
                    professor.get().getUpdatedAt(),
                    eventsList.stream().filter(
                        e -> e.getProfessorId().equals(event.getProfessorId())
                    ).toList()
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