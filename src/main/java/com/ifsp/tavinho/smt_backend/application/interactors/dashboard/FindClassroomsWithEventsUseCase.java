package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.ClassroomWithEventsDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindClassroomsWithEventsUseCase implements UseCase<Classroom, ClassroomWithEventsDTO> {

    private final EventRepository eventRepository;

    @Override
    public ClassroomWithEventsDTO execute(Classroom classroom) {
        List<Event> classroomEvents = this.eventRepository.findByClassroomId(classroom.getId());

        return new ClassroomWithEventsDTO(
            classroom.getId(),
            classroom.getDescription(),
            classroom.getBlock(),
            classroom.getFloor(),
            classroom.getCapacity(),
            classroom.getObservation(),
            classroom.getCreatedAt(),
            classroom.getUpdatedAt(),
            classroomEvents
        );
    }
    
}
