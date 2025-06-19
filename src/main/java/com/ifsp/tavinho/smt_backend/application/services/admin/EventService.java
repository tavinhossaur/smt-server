package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.UpdateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.DeleteEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.FindEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.ListEventsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final CreateEventUseCase createEvent;
    private final UpdateEventUseCase updateEvent;
    private final DeleteEventUseCase deleteEvent;
    private final FindEventUseCase findEvent;
    private final ListEventsUseCase listEvents;

    public Event create(EventDTO input) {
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
    
}
