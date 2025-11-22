package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.UpdateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.DeleteEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.FindEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.ListEventsUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CheckAvailabilityUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final CreateEventUseCase createEvent;
    private final UpdateEventUseCase updateEvent;
    private final DeleteEventUseCase deleteEvent;
    private final FindEventUseCase findEvent;
    private final ListEventsUseCase listEvents;

    private final CheckAvailabilityUseCase checkAvailability;

    public Event create(EventDTO input) {
        if (!this.checkAvailability.checkProfessorAvailability(input, input.professorId())) {
            throw new AppError("This professor is already attached to an event at the desired day and time.", HttpStatus.CONFLICT);
        }

        if (!this.checkAvailability.checkClassroomAvailability(input, input.classroomId())) {
            throw new AppError("This classroom is already attached to an event at the desired day and time.", HttpStatus.CONFLICT);
        }

        if (this.checkRetroactivity(LocalTime.parse(input.startTime()), LocalTime.parse(input.endTime()))) {
            throw new AppError("The start time and end time of the event are retroactive to each other.", HttpStatus.BAD_REQUEST);
        }

        return this.createEvent.execute(input);
    }

    public Event update(EventDTO input, String id) {
        Event event = this.findEvent.execute(id);
        return this.updateEvent.execute(input, event);
    }

    public Boolean delete(String id) {
        Event event = this.findEvent.execute(id);
        return this.deleteEvent.execute(event);
    }

    public Event find(String id) {
        return this.findEvent.execute(id);
    }

    public List<Event> list() {
        return this.listEvents.execute(null);
    }

    private Boolean checkRetroactivity(LocalTime startTime, LocalTime endTime) {
        return startTime.isAfter(endTime);
    }
    
}
