package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEventUseCase implements UseCase<EventDTO, Event> {

    private final EventRepository eventRepository;

    @Override
    public Event execute(EventDTO input) {
        return this.eventRepository.save(
            new Event(
                input.description(), 
                input.weekday(), 
                LocalTime.parse(input.startTime()), 
                LocalTime.parse(input.endTime()), 
                input.classroomId(),
                input.professorId(),
                input.disciplineId(),
                input.courseId()
            )
        );
    }
    
}
