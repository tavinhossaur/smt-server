package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindEventUseCase implements UseCase<String, Event> {

    private final EventRepository repository;

    @Override
    public Event execute(String id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }
    
}
