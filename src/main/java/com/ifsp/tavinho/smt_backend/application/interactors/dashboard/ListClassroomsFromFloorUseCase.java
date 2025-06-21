package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListClassroomsFromFloorUseCase implements UseCase<String, List<Classroom>> {
    
    private final ClassroomRepository classroomRepository;
    
    @Override
    public List<Classroom> execute(String floor) {
        return this.classroomRepository.findAllByFloor(floor);
    }

}
