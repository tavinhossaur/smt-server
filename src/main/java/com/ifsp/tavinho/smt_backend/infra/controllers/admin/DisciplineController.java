package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.CreateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.DeleteDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.FindDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.ListDisciplinesUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.UpdateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_DISCIPLINES;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_DISCIPLINES)
public class DisciplineController implements CrudOperations<DisciplineDTO, Discipline> {
    
    private final CreateDisciplineUseCase createDiscipline;
    private final UpdateDisciplineUseCase updateDiscipline;
    private final DeleteDisciplineUseCase deleteDiscipline;
    private final FindDisciplineUseCase findDiscipline;
    private final ListDisciplinesUseCase listDisciplines;

    @Override
    public ResponseEntity<Discipline> create(@Valid DisciplineDTO input) {
        return this.createDiscipline.execute(input);
    }

    @Override
    public ResponseEntity<Discipline> update(DisciplineDTO input, String id) {
        return this.updateDiscipline.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.deleteDiscipline.execute(id);
    }

    @Override
    public ResponseEntity<Discipline> find(String id) {
        return this.findDiscipline.execute(id);
    }

    @Override
    public ResponseEntity<List<Discipline>> list() {
        return this.listDisciplines.execute(null);
    }

}
