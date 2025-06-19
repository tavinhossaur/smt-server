package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListEventsUseCase implements UseCase<Void, List<Event>> {

    private final EventRepository repository;

    @Override
    public List<Event> execute(Void v) {
        return this.repository.findAll();
    }
    
}
