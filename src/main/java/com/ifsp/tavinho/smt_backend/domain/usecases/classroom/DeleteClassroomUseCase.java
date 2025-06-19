package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteClassroomUseCase implements UseCase<Classroom, Boolean> {

    private final ClassroomRepository repository;

    @Override
    public Boolean execute(Classroom classroom) {
        this.repository.delete(classroom);
        return true;
    }
    
}
