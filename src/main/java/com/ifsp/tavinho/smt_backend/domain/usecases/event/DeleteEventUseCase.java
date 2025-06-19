package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteEventUseCase implements UseCase<Event, Boolean> {

    private final EventRepository repository;

    @Override
    public Boolean execute(Event event) {
        this.repository.delete(event);
        return true;
    }
    
}
