package com.ifsp.tavinho.smt_backend.domain.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;

public interface EventRepository extends EntityRepository<Event> {
    List<Event> findByProfessorId(String id);
    List<Event> findByCourseId(String courseId); 
    List<Event> findByWeekdayAndCourseId(String weekday, String courseId);
}
