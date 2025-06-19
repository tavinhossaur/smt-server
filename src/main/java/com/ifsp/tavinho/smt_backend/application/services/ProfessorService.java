package com.ifsp.tavinho.smt_backend.application.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.CreateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.UpdateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.DeleteProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.FindProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.ListProfessorsUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final CreateProfessorUseCase createProfessor;
    private final UpdateProfessorUseCase updateProfessor;
    private final DeleteProfessorUseCase deleteProfessor;
    private final FindProfessorUseCase findProfessor;
    private final ListProfessorsUseCase listProfessors;

    public ResponseEntity<Professor> create(ProfessorDTO input) {
        return this.createProfessor.execute(input);
    }

    public ResponseEntity<Professor> update(ProfessorDTO input, String id) {
        return this.updateProfessor.execute(input, id);
    }

    public ResponseEntity<ApiResponse<Void>> delete(String id) {
        return this.deleteProfessor.execute(id);
    }

    public ResponseEntity<Professor> find(String id) {
        return this.findProfessor.execute(id);
    }

    public ResponseEntity<List<Professor>> list() {
        return this.listProfessors.execute(null);
    }
    
}
