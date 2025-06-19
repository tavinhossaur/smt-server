package com.ifsp.tavinho.smt_backend.domain.usecases.classroom;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListClassroomsUseCase implements UseCase<Void, List<Classroom>> {

    private final ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> execute(Void _unused) {
        return this.classroomRepository.findAll();
    }
    
}
