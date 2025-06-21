package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.SearchQueryResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchProfessorsAndClassroomsUseCase implements UseCase<String, SearchQueryResponseDTO> {

    private final ProfessorRepository professorRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public SearchQueryResponseDTO execute(String query) {
        return new SearchQueryResponseDTO(
            this.professorRepository.searchProfessors(query),
            this.classroomRepository.searchClassrooms(query)
        );
    }
    
}
