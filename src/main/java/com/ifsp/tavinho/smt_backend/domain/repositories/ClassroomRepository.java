package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;

public interface ClassroomRepository extends EntityRepository<Classroom> { 
    List<Classroom> findAllByFloor(String floor);
}
