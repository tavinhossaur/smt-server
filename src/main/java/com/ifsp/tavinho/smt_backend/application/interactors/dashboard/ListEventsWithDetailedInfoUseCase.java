package com.ifsp.tavinho.smt_backend.application.interactors.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListEventsWithDetailedInfoUseCase implements UseCase<Void, List<EventDetailsResponseDTO>> {

    private final EventRepository eventRepository;
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;

    @Override
    public List<EventDetailsResponseDTO> execute(Void _unused) {
        List<Event> eventsList = this.eventRepository.findAll();

        List<EventDetailsResponseDTO> eventsWithDetailsList = new ArrayList<>();

        for (Event event : eventsList) {            
            Optional<Classroom> classroom = this.classroomRepository.findById(event.getClassroomId());
            Optional<Professor> professor = this.professorRepository.findById(event.getProfessorId());
            Optional<Course> course = this.courseRepository.findById(event.getCourseId());
            Optional<Discipline> discipline = this.disciplineRepository.findById(event.getDisciplineId());

            eventsWithDetailsList.add(
                new EventDetailsResponseDTO(
                    event.getId(),
                    event.getDescription(),
                    event.getWeekday(),
                    event.getStartTime(),
                    event.getEndTime(),
                    classroom.orElse(null),
                    professor.orElse(null),
                    course.orElse(null),
                    discipline.orElse(null)
                )
            );
        }

        return eventsWithDetailsList;
    }
    
}
