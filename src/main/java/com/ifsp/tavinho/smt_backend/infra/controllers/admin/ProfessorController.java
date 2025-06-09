package com.ifsp.tavinho.smt_backend.infra.controllers.admin;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.CreateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.DeleteProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.FindProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.ListProfessorsUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.UpdateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.infra.interfaces.CrudOperations;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import static com.ifsp.tavinho.smt_backend.infra.routes.Routes.ADMIN_PROFESSORS;

@RestController
@AllArgsConstructor
@RequestMapping(ADMIN_PROFESSORS)
public class ProfessorController implements CrudOperations<ProfessorDTO, Professor> {
    
    private CreateProfessorUseCase createProfessor;
    private UpdateProfessorUseCase updateProfessor;
    private DeleteProfessorUseCase deleteProfessor;
    private FindProfessorUseCase findProfessor;
    private ListProfessorsUseCase listProfessors;

    @Override
    public ResponseEntity<Professor> create(ProfessorDTO input) {
        return createProfessor.execute(input);
    }

    @Override
    public ResponseEntity<Professor> update(ProfessorDTO input, String id) {
        return updateProfessor.execute(input, id);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return deleteProfessor.execute(id);
    }

    @Override
    public ResponseEntity<Professor> find(String id) {
        return findProfessor.execute(id);
    }

    @Override
    public ResponseEntity<List<Professor>> list() {
        return listProfessors.execute(null);
    }

}
