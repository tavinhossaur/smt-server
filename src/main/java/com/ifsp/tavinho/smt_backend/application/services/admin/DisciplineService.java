package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.DisciplineDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.CreateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.UpdateDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.DeleteDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.FindDisciplineUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.discipline.ListDisciplinesUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisciplineService {

    private final CreateDisciplineUseCase createDiscipline;
    private final UpdateDisciplineUseCase updateDiscipline;
    private final DeleteDisciplineUseCase deleteDiscipline;
    private final FindDisciplineUseCase findDiscipline;
    private final ListDisciplinesUseCase listDisciplines;

    public Discipline create(DisciplineDTO input) {
        return this.createDiscipline.execute(input);
    }

    public Discipline update(DisciplineDTO input, String id) {
        Discipline discipline = this.findDiscipline.execute(id);
        return this.updateDiscipline.execute(input, discipline);
    }

    public Boolean delete(String id) {
        Discipline discipline = this.findDiscipline.execute(id);
        return this.deleteDiscipline.execute(discipline);
    }

    public Discipline find(String id) {
        return this.findDiscipline.execute(id);
    }

    public List<Discipline> list() {
        return this.listDisciplines.execute(null);
    }
    
}
