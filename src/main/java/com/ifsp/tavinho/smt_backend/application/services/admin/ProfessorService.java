package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.ProfessorDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.CreateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.UpdateProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.DeleteProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.FindProfessorUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.professor.ListProfessorsUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.IsEntityLinkedToEventUseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final CreateProfessorUseCase createProfessor;
    private final UpdateProfessorUseCase updateProfessor;
    private final DeleteProfessorUseCase deleteProfessor;
    private final FindProfessorUseCase findProfessor;
    private final ListProfessorsUseCase listProfessors;

    private final IsEntityLinkedToEventUseCase isEntityLinkedToEvent;

    public Professor create(ProfessorDTO input) {
        return this.createProfessor.execute(input);
    }

    public Professor update(ProfessorDTO input, String id) {
        Professor professor = this.findProfessor.execute(id);
        return this.updateProfessor.execute(input, professor);
    }

    public Boolean delete(String id) {
        Professor professor = this.findProfessor.execute(id);

        if (this.isEntityLinkedToEvent.execute(professor)) {
            throw new AppError("Professor could not be deleted because it is linked to an event.", HttpStatus.CONFLICT);
        }

        return this.deleteProfessor.execute(professor);
    }

    public Professor find(String id) {
        return this.findProfessor.execute(id);
    }

    public List<Professor> list() {
        return this.listProfessors.execute(null);
    }
    
}
