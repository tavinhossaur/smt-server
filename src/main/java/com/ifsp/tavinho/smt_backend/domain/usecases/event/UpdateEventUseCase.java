package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateEventUseCase implements UseCase<EventDTO, Event> {

    private final EventRepository repository;

    @Override
    public Event execute(EventDTO input, Event existing) {
        if (input.description() != null) existing.setDescription(input.description());
        if (input.weekday() != null) existing.setWeekday(input.weekday());
        if (input.startTime() != null) existing.setStartTime(LocalTime.parse(input.startTime()));
        if (input.endTime() != null) existing.setEndTime(LocalTime.parse(input.endTime()));
        if (input.classroomId() != null) existing.setClassroomId(input.classroomId());
        if (input.professorId() != null) existing.setProfessorId(input.professorId());
        if (input.disciplineId() != null) existing.setDisciplineId(input.disciplineId());
        if (input.courseId() != null) existing.setCourseId(input.courseId());

        return this.repository.save(existing);
    }

    @Override
    public Event execute(EventDTO input) {
        throw new UnsupportedOperationException("An existing event is required for update.");
    }
    
}
