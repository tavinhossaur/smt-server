package com.ifsp.tavinho.smt_backend.domain.usecases.professor;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListProfessorsWithEventsUseCase implements UseCase<String, List<ProfessorWithEventsDTO>> {

    private final ProfessorRepository professorRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<List<ProfessorWithEventsDTO>> execute(String weekday, String courseId) {
        if (weekday.isBlank() || weekday == null || courseId.isBlank() || courseId == null) {
            throw new AppError("Weekday and course values must be provided.", HttpStatus.BAD_REQUEST);
        }

        List<ProfessorWithEventsDTO> professorsWithEventsList = new ArrayList<>();

        List<Event> eventsList = this.eventRepository.findByWeekdayAndCourseId(weekday, courseId);

        for (Event event : eventsList) {
            Optional<Professor> professor = professorRepository.findById(event.getProfessorId());

            if (!professor.isPresent()) continue;

            professorsWithEventsList.add(
                new ProfessorWithEventsDTO(
                    professor.get(), 
                    eventsList.stream().filter(
                        e -> e.getProfessorId().equals(event.getProfessorId())
                    ).toList()
                )
            );
        }

        return ResponseEntity.ok(professorsWithEventsList);
    }

    @Override
    public ResponseEntity<List<ProfessorWithEventsDTO>> execute(String _unused) {
        throw new UnsupportedOperationException("Course ID is required for this query.");
    }
    
}