package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.output.EventDetailsResponseSimplifiedDTO;

public interface EventAggregationRepository {
    List<EventDetailsResponseSimplifiedDTO> findAllWithMinimalDetails();
}
