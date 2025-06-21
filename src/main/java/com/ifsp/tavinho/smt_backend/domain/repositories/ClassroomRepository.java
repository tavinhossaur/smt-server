package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;

public interface ClassroomRepository extends EntityRepository<Classroom> { 
    List<Classroom> findAllByFloor(String floor);

    @Query("{ '$or': [ { 'description': { $regex: ?0, $options: 'i' } }, { 'observation': { $regex: ?0, $options: 'i' } } ] }")
    List<Classroom> searchClassrooms(String search);
}








