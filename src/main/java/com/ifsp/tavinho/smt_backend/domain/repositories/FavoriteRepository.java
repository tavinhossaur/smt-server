package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    Optional<Favorite> findByUserIdAndProfessorId(String userId, String professorId);
    List<Favorite> findAllByUserId(String id); 
}
