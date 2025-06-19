package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.application.services.ProfessorService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_PROFESSORS;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_PROFESSORS)
public class ProfessorController implements EntityController<ProfessorDTO, Professor> {
    
    private final ProfessorService professorService;

    @Override
    public ResponseEntity<Professor> create(@Valid ProfessorDTO input) {
        return this.professorService.create(input);
    }

    @Override
    public ResponseEntity<Professor> update(ProfessorDTO input, String id) {
        return this.professorService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.professorService.delete(id);
    }

    @Override
    public ResponseEntity<Professor> find(String id) {
        return this.professorService.find(id);
    }

    @Override
    public ResponseEntity<List<Professor>> list() {
        return this.professorService.list();
    }

}
