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

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_EVENTS;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_EVENTS)
public class EventController implements CrudOperations<EventDTO, Event> {
    
    private CreateEventUseCase createEvent;
    private UpdateEventUseCase updateEvent;
    private DeleteEventUseCase deleteEvent;
    private FindEventUseCase findEvent;
    private ListEventsUseCase listEvents;

    @Override
    public ResponseEntity<Event> create(EventDTO input) {
        return createEvent.execute(input);
    }

    @Override
    public ResponseEntity<Event> update(EventDTO input, String id) {
        return updateEvent.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return deleteEvent.execute(id);
    }

    @Override
    public ResponseEntity<Event> find(String id) {
        return findEvent.execute(id);
    }

    @Override
    public ResponseEntity<List<Event>> list() {
        return listEvents.execute(null);
    }

}
