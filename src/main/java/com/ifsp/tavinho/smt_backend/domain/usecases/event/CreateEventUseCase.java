package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class CreateEventUseCase implements UseCase<EventDTO, Event> {

    @Autowired
    private EventRepository repository;

    @Override
    public ResponseEntity<Event> execute(EventDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            this.repository.save(
                new Event(
                    input.description(), 
                    input.weekday(), 
                    input.startTime(), 
                    input.endTime(), 
                    input.classroomId(),
                    input.professorId(),
                    input.courseId(),
                    input.disciplineId()
                )
            )
        );
    }
    
}
