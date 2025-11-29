package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.input.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final EventRepository eventRepository;

    public Classroom create(ClassroomDTO input) {
        return this.classroomRepository.save(
            new Classroom(
                input.description(),
                input.block(),
                input.floor(),
                input.capacity(),
                input.observation()
            )
        );
    }

    public Classroom update(ClassroomDTO input, String id) {
        Classroom existing = this.find(id);
        
        if (input.description() != null) existing.setDescription(input.description());
        if (input.block() != null) existing.setBlock(input.block());
        if (input.floor() != null) existing.setFloor(input.floor());
        if (input.capacity() != null) existing.setCapacity(input.capacity());
        if (input.observation() != null) existing.setObservation(input.observation());

        return this.classroomRepository.save(existing);
    }

    public Boolean delete(String id) {
        Classroom classroom = this.find(id);

        if (this.eventRepository.existsByClassroomId(classroom.getId())) {
            throw new AppError("Classroom could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        this.classroomRepository.delete(classroom);
        return true;
    }

    public Classroom find(String id) {
        return this.classroomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + id));
    }

    public List<Classroom> list() {
        return this.classroomRepository.findAllByOrderByDescriptionAscFloorAsc();
    }
    
} 