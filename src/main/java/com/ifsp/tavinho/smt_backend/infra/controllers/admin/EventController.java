package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.DeleteEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.FindEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.ListEventsUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.UpdateEventUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_EVENTS;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_EVENTS)
public class EventController implements CrudOperations<EventDTO, Event> {
    
    private final CreateEventUseCase createEvent;
    private final UpdateEventUseCase updateEvent;
    private final DeleteEventUseCase deleteEvent;
    private final FindEventUseCase findEvent;
    private final ListEventsUseCase listEvents;

    @Override
    public ResponseEntity<Event> create(@Valid EventDTO input) {
        return this.createEvent.execute(input);
    }

    @Override
    public ResponseEntity<Event> update(EventDTO input, String id) {
        return this.updateEvent.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.deleteEvent.execute(id);
    }

    @Override
    public ResponseEntity<Event> find(String id) {
        return this.findEvent.execute(id);
    }

    @Override
    public ResponseEntity<List<Event>> list() {
        return this.listEvents.execute(null);
    }

}
