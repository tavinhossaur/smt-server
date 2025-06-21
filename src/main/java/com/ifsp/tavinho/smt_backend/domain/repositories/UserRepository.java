package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.ifsp.tavinho.smt_backend.domain.entities.User;

public interface UserRepository extends EntityRepository<User> {
    List<User> findAllByOrderByFullNameAsc();
    Optional<User> findByEmail(String email);
}
