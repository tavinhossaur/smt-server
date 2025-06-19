package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ClassroomDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.application.services.ClassroomService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_CLASSROOMS;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_CLASSROOMS)
public class ClassroomController implements EntityController<ClassroomDTO, Classroom> {
    
    private final ClassroomService classroomService;

    @Override
    public ResponseEntity<Classroom> create(@Valid ClassroomDTO input) {
        return this.classroomService.create(input);
    }

    @Override
    public ResponseEntity<Classroom> update(ClassroomDTO input, String id) {
        return this.classroomService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.classroomService.delete(id);
    }

    @Override
    public ResponseEntity<Classroom> find(String id) {
        return this.classroomService.find(id);
    }

    @Override
    public ResponseEntity<List<Classroom>> list() {
        return this.classroomService.list();
    }

}
