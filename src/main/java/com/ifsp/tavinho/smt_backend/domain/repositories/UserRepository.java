package com.ifsp.tavinho.smt_backend.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.tavinho.smt_backend.domain.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    // Optional<User> findByUsername(String username);
}
