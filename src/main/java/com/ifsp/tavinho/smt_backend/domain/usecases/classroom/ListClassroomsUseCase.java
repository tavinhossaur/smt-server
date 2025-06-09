package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListClassroomsUseCase implements UseCase<String, List<Classroom>> {

    private final ClassroomRepository classroomRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<List<Classroom>> execute(String floor, String courseId) {
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
        return ResponseEntity.ok(this.classroomRepository.findAll());
    }
    
}
