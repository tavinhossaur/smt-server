package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListEventsWithDetailedInfoUseCase implements UseCase<Void, List<EventDetailsResponseDTO>> {

    private final EventRepository eventRepository;

    @Override
    public List<EventDetailsResponseDTO> execute(Void _unused) {
        return this.eventRepository.findAllWithMinimalDetails();
    }
    
}
