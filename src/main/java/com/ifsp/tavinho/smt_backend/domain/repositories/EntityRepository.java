package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {
    T save(T entity);
    Optional<T> findById(String id);
    List<T> findAll();
    void delete(T entity);
    boolean existsById(String id);
}
