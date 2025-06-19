package com.ifsp.tavinho.smt_backend.application.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.CreateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.UpdateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.DeleteDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.FindDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.ListDisciplinesUseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ServerApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final CreateDisciplineUseCase createDiscipline;
    private final UpdateDisciplineUseCase updateDiscipline;
    private final DeleteDisciplineUseCase deleteDiscipline;
    private final FindDisciplineUseCase findDiscipline;
    private final ListDisciplinesUseCase listDisciplines;

    public ResponseEntity<Discipline> create(DisciplineDTO input) {
        return this.createDiscipline.execute(input);
    }

    public ResponseEntity<Discipline> update(DisciplineDTO input, String id) {
        return this.updateDiscipline.execute(input, id);
    }

    public ResponseEntity<ServerApiResponse<Void>> delete(String id) {
        return this.deleteDiscipline.execute(id);
    }

    public ResponseEntity<Discipline> find(String id) {
        return this.findDiscipline.execute(id);
    }

    public ResponseEntity<List<Discipline>> list() {
        return this.listDisciplines.execute(null);
    }
    
}
