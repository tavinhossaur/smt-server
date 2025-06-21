package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.output.EventDetailsResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindEventDetailedInfoUseCase implements UseCase<String, EventDetailsResponseDTO> {

    private final EventRepository eventRepository;
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;

    @Override
    public EventDetailsResponseDTO execute(String id) {
        Event event = this.eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
        Classroom classroom = this.classroomRepository.findById(event.getClassroomId()).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + event.getClassroomId()));
        Professor professor = this.professorRepository.findById(event.getProfessorId()).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + event.getProfessorId()));
        Course course = this.courseRepository.findById(event.getCourseId()).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + event.getCourseId()));
        Discipline discipline = this.disciplineRepository.findById(event.getDisciplineId()).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + event.getDisciplineId()));

        return new EventDetailsResponseDTO(
            event.getId(),
            event.getDescription(),
            event.getWeekday(),
            event.getStartTime(),
            event.getEndTime(),
            classroom,
            professor,
            course,
            discipline
        );
    }
    
}
