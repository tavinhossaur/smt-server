package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.UpdateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.DeleteEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.FindEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.ListEventsUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final CreateEventUseCase createEvent;
    private final UpdateEventUseCase updateEvent;
    private final DeleteEventUseCase deleteEvent;
    private final FindEventUseCase findEvent;
    private final ListEventsUseCase listEvents;

    public ResponseEntity<Event> create(EventDTO input) {
        return this.createEvent.execute(input);
    }

    public ResponseEntity<Event> update(EventDTO input, String id) {
        return this.updateEvent.execute(input, id);
    }

    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.deleteEvent.execute(id);
    }

    public ResponseEntity<Event> find(String id) {
        return this.findEvent.execute(id);
    }

    public ResponseEntity<List<Event>> list() {
        return this.listEvents.execute(null);
    }
    
}
