package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListEventsUseCase implements UseCase<Void, List<Event>> {

    private final EventRepository repository;

    @Override
    public ResponseEntity<List<Event>> execute(Void v) {
        return ResponseEntity.ok(this.repository.findAll());
    }
    
}
