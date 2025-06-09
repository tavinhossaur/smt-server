package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class FindEventUseCase implements UseCase<String, Event> {

    @Autowired
    private EventRepository repository;

    @Override
    public ResponseEntity<Event> execute(String id) {
        return ResponseEntity.ok(this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id)));
    }
    
}
