package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.BaseEntity;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

@Service
public class IsEntityLinkedToEventUseCase implements UseCase<BaseEntity, Boolean> {

    private final EventRepository eventRepository;
    private final Map<Class<? extends BaseEntity>, Function<String, Boolean>> existsByIdMap;

    public IsEntityLinkedToEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.existsByIdMap = Map.of(
            Professor.class, this.eventRepository::existsByProfessorId,
            Classroom.class, this.eventRepository::existsByClassroomId,
            Course.class, this.eventRepository::existsByCourseId,
            Discipline.class, this.eventRepository::existsByDisciplineId
        );
    }

    @Override
    public Boolean execute(BaseEntity entity) {
        return existsByIdMap.get(entity.getClass()).apply(entity.getId());
    }

} 