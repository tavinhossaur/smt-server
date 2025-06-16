package com.ifsp.tavinho.smt_backend.infra.repositories;

import com.ifsp.tavinho.smt_backend.domain.entities.Favorite;
import com.ifsp.tavinho.smt_backend.domain.repositories.FavoriteRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFavoriteRepository extends MongoRepository<Favorite, String>, FavoriteRepository { } 