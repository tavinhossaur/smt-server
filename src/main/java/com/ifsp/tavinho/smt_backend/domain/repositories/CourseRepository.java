package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;

public interface CourseRepository extends EntityRepository<Course> { 
    List<Course> findAllByOrderByNameAsc();
}
