package com.ifsp.tavinho.smt_backend.infra.repositories;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProfessorRepository extends MongoRepository<Professor, String>, ProfessorRepository { } 