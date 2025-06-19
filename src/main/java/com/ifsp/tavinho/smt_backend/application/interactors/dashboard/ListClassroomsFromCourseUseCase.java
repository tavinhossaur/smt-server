package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListClassroomsFromCourseUseCase implements UseCase<String, List<Classroom>> {
    
    private final ClassroomRepository classroomRepository;
    private final EventRepository eventRepository;
    private final CourseRepository courseRepository;
    
    @Override
    public ResponseEntity<List<Classroom>> execute(String floor, String courseId) {
        if (floor.isBlank() || floor == null || courseId.isBlank() || courseId == null) {
            throw new AppError("Floor and course values must be provided.", HttpStatus.BAD_REQUEST);
        }

        this.courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        List<Event> eventsList = this.eventRepository.findByCourseId(courseId);

        List<Classroom> classroomsList = new ArrayList<>();

        for (Event event : eventsList) {
            Optional<Classroom> classroom = this.classroomRepository.findById(event.getClassroomId());

            if (!classroom.isPresent()) continue;
            if (!classroom.get().getFloor().equals(floor)) continue;

            classroomsList.add(classroom.get());
        }

        return ResponseEntity.ok(classroomsList);
    }

    @Override
    public ResponseEntity<List<Classroom>> execute(String _unused) {
        throw new UnsupportedOperationException("Course ID is required for this query.");
    }

}
