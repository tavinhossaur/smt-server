package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;

public interface FavoriteRepository extends EntityRepository<Favorite> {
    Optional<Favorite> findByUserIdAndProfessorId(String userId, String professorId);
    List<Favorite> findAllByUserId(String id); 
}
