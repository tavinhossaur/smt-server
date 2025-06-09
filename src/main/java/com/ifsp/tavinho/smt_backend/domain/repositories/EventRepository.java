package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByProfessorId(String id);
    List<Event> findByCourseId(String courseId); 
    List<Event> findByWeekdayAndCourseId(String weekday, String courseId);
}
