package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.ifsp.tavinho.smt_backend.domain.entities.Professor;

public interface ProfessorRepository extends EntityRepository<Professor> {
    @Query("{ '$or': [ { 'name': { $regex: ?0, $options: 'i' } }, { 'email': { $regex: ?0, $options: 'i' } } ] }")
    List<Professor> searchProfessors(String search);
}
