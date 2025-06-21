package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;

public interface DisciplineRepository extends EntityRepository<Discipline> {
    List<Discipline> findAllByOrderByNameAsc();
}
