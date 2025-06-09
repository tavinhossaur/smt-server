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

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_DISCIPLINES;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_DISCIPLINES)
public class DisciplineController implements CrudOperations<DisciplineDTO, Discipline> {
    
    private CreateDisciplineUseCase createDiscipline;
    private UpdateDisciplineUseCase updateDiscipline;
    private DeleteDisciplineUseCase deleteDiscipline;
    private FindDisciplineUseCase findDiscipline;
    private ListDisciplinesUseCase listDisciplines;

    @Override
    public ResponseEntity<Discipline> create(DisciplineDTO input) {
        return createDiscipline.execute(input);
    }

    @Override
    public ResponseEntity<Discipline> update(DisciplineDTO input, String id) {
        return updateDiscipline.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return deleteDiscipline.execute(id);
    }

    @Override
    public ResponseEntity<Discipline> find(String id) {
        return findDiscipline.execute(id);
    }

    @Override
    public ResponseEntity<List<Discipline>> list() {
        return listDisciplines.execute(null);
    }

}
