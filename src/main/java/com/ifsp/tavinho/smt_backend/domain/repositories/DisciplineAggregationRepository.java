package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.application.dtos.output.DisciplineDetailsResponseSimplifiedDTO;

public interface DisciplineAggregationRepository {
    List<DisciplineDetailsResponseSimplifiedDTO> findAllWithCourses();
}
