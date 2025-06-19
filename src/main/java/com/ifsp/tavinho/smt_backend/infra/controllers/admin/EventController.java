package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.application.services.EventService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_EVENTS;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_EVENTS)
public class EventController implements EntityController<EventDTO, Event> {
    
    private final EventService eventService;

    @Override
    public ResponseEntity<Event> create(@Valid EventDTO input) {
        return this.eventService.create(input);
    }

    @Override
    public ResponseEntity<Event> update(EventDTO input, String id) {
        return this.eventService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.eventService.delete(id);
    }

    @Override
    public ResponseEntity<Event> find(String id) {
        return this.eventService.find(id);
    }

    @Override
    public ResponseEntity<List<Event>> list() {
        return this.eventService.list();
    }

}
