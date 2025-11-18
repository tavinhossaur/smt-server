package com.ifsp.tavinho.smt_backend.infra.repositories;

import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineAggregationRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDisciplineRepository extends MongoRepository<Discipline, String>, DisciplineRepository, DisciplineAggregationRepository { }