package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.CreateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.UpdateClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.DeleteClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.FindClassroomUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.classroom.ListClassroomsUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_CLASSROOMS;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_CLASSROOMS)
public class ClassroomController implements CrudOperations<ClassroomDTO, Classroom> {
    
    private CreateClassroomUseCase createClassroom;
    private UpdateClassroomUseCase updateClassroom;
    private DeleteClassroomUseCase deleteClassroom;
    private FindClassroomUseCase findClassroom;
    private ListClassroomsUseCase listClassrooms;

    @Override
    public ResponseEntity<Classroom> create(ClassroomDTO input) {
        return createClassroom.execute(input);
    }

    @Override
    public ResponseEntity<Classroom> update(ClassroomDTO input, String id) {
        return updateClassroom.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return deleteClassroom.execute(id);
    }

    @Override
    public ResponseEntity<Classroom> find(String id) {
        return findClassroom.execute(id);
    }

    @Override
    public ResponseEntity<List<Classroom>> list() {
        return listClassrooms.execute(null);
    }

}
