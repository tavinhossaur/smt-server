package com.ifsp.tavinho.smt_backend.application.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.CreateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.UpdateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.DeleteClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.FindClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.ListClassroomsUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final CreateClassroomUseCase createClassroom;
    private final UpdateClassroomUseCase updateClassroom;
    private final DeleteClassroomUseCase deleteClassroom;
    private final FindClassroomUseCase findClassroom;
    private final ListClassroomsUseCase listClassrooms;

    public ResponseEntity<Classroom> create(ClassroomDTO input) {
        return this.createClassroom.execute(input);
    }

    public ResponseEntity<Classroom> update(ClassroomDTO input, String id) {
        return this.updateClassroom.execute(input, id);
    }

    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.deleteClassroom.execute(id);
    }

    public ResponseEntity<Classroom> find(String id) {
        return this.findClassroom.execute(id);
    }

    public ResponseEntity<List<Classroom>> list() {
        return this.listClassrooms.execute(null);
    }
    
} 