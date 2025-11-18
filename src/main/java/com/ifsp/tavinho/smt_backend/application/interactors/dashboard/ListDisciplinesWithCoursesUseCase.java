package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.infra.repositories.MongoDisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.dtos.output.DisciplineDetailsResponseSimplifiedDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListDisciplinesWithCoursesUseCase implements UseCase<Void, List<DisciplineDetailsResponseSimplifiedDTO>> {

    private final MongoDisciplineRepository disciplineRepository;

    @Override
    public List<DisciplineDetailsResponseSimplifiedDTO> execute(Void _unused) {
        return this.disciplineRepository.findAllWithCourses();
    }
    
}
