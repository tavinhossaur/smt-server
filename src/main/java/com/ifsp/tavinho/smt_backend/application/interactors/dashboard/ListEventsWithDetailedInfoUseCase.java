package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.infra.repositories.MongoEventRepository;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseSimplifiedDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListEventsWithDetailedInfoUseCase implements UseCase<Void, List<EventDetailsResponseSimplifiedDTO>> {

    private final MongoEventRepository eventRepository;

    @Override
    public List<EventDetailsResponseSimplifiedDTO> execute(Void _unused) {
        return this.eventRepository.findAllWithMinimalDetails();
    }
    
}
