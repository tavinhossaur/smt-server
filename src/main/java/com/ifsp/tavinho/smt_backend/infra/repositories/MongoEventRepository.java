package com.ifsp.tavinho.smt_backend.infra.repositories;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseDTO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEventRepository extends MongoRepository<Event, String>, EventRepository {
    
    @Aggregation(pipeline = {
        "{ $lookup: { from: 'classrooms', localField: 'classroomId', foreignField: '_id', as: 'classroom' } }",
        "{ $lookup: { from: 'professors', localField: 'professorId', foreignField: '_id', as: 'professor' } }",
        "{ $lookup: { from: 'courses', localField: 'courseId', foreignField: '_id', as: 'course' } }",
        "{ $lookup: { from: 'disciplines', localField: 'disciplineId', foreignField: '_id', as: 'discipline' } }",
        "{ $project: { " +
            "id: 1, description: 1, weekday: 1, startTime: 1, endTime: 1, " +
            "classroom: { $arrayElemAt: [ '$classroom', 0 ] }, " +
            "professor: { $arrayElemAt: [ '$professor', 0 ] }, " +
            "course: { $arrayElemAt: [ '$course', 0 ] }, " +
            "discipline: { $arrayElemAt: [ '$discipline', 0 ] } } }",
        "{ $project: { " +
            "id: 1, description: 1, weekday: 1, startTime: 1, endTime: 1, " +
            "classroom: { id: '$classroom._id', description: '$classroom.description' }, " +
            "professor: { id: '$professor._id', description: '$professor.name' }, " +
            "course: { id: '$course._id', description: '$course.abbreviation' }, " +
            "discipline: { id: '$discipline._id', description: '$discipline.abbreviation' } } }"
    })
    List<EventDetailsResponseDTO> findAllWithMinimalDetails();

} 