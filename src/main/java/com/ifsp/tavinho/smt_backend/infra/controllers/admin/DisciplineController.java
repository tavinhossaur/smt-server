package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.application.services.DisciplineService;
import com.ifsp.tavinho.smt_backend.infra.interfaces.EntityController;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_DISCIPLINES;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN_DISCIPLINES)
public class DisciplineController implements EntityController<DisciplineDTO, Discipline> {
    
    private final DisciplineService disciplineService;

    @Override
    public ResponseEntity<Discipline> create(@Valid DisciplineDTO input) {
        return this.disciplineService.create(input);
    }

    @Override
    public ResponseEntity<Discipline> update(DisciplineDTO input, String id) {
        return this.disciplineService.update(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.disciplineService.delete(id);
    }

    @Override
    public ResponseEntity<Discipline> find(String id) {
        return this.disciplineService.find(id);
    }

    @Override
    public ResponseEntity<List<Discipline>> list() {
        return this.disciplineService.list();
    }

}
