package com.ifsp.tavinho.smt_backend.application.services.guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.application.dtos.output.ClassroomWithEventsDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.DisciplineDetailsResponseSimplifiedDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.EventDetailsResponseDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.EventDetailsResponseSimplifiedDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.ProfessorWithEventsDTO;
import com.ifsp.tavinho.smt_backend.application.dtos.output.SearchQueryResponseDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineAggregationRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventAggregationRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EventRepository eventRepository;
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;

    private final EventAggregationRepository eventAggregationRepository;
    private final DisciplineAggregationRepository disciplineAggregationRepository;

    public EventDetailsResponseDTO getDetailedEventInfo(String id) {
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
    
    public ProfessorWithEventsDTO getProfessorWithWeekEventsList(String id) {
        Professor professor = this.professorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + id));
        
        List<Event> professorEvents = this.eventRepository.findByProfessorId(professor.getId());

        return new ProfessorWithEventsDTO(
            professor.getId(),
            professor.getName(),
            professor.getEmail(),
            professor.getCreatedAt(),
            professor.getUpdatedAt(),
            professorEvents
        );
    }

    public List<EventDetailsResponseSimplifiedDTO> listEventsWithDetailedInfo() {
        return this.eventAggregationRepository.findAllWithMinimalDetails();
    }

    public List<DisciplineDetailsResponseSimplifiedDTO> listDisciplinesWithCourses() {
        return this.disciplineAggregationRepository.findAllWithCourses();
    }

    public List<ProfessorWithEventsDTO> getProfessorsWithWeekEventsListFromDayAndCourse(String weekdayName, String courseId) {
        if (weekdayName.isBlank() || weekdayName == null || courseId.isBlank() || courseId == null) {
            throw new AppError("Weekday and course values must be provided.", HttpStatus.BAD_REQUEST);
        }

        Weekday weekday = Weekday.valueOf(weekdayName.toUpperCase());

        this.courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        List<Event> eventsList = this.eventRepository.findByWeekdayAndCourseId(weekday.name(), courseId);
    
        Map<String, List<Event>> eventsByProfessor = eventsList.stream().collect(Collectors.groupingBy(Event::getProfessorId));
    
        List<ProfessorWithEventsDTO> professorsWithEventsList = new ArrayList<>();
    
        for (Map.Entry<String, List<Event>> entry : eventsByProfessor.entrySet()) {
            String professorId = entry.getKey();
            List<Event> professorEvents = entry.getValue();
    
            Optional<Professor> professor = this.professorRepository.findById(professorId);
            
            if (professor.isEmpty()) continue;
    
            professorsWithEventsList.add(
                new ProfessorWithEventsDTO(
                    professor.get().getId(),
                    professor.get().getName(),
                    professor.get().getEmail(),
                    professor.get().getCreatedAt(),
                    professor.get().getUpdatedAt(),
                    professorEvents
                )
            );
        }
    
        return professorsWithEventsList;
    }

    public ClassroomWithEventsDTO getClassroomWithEvents(String id) {
        Classroom classroom = this.classroomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + id));

        List<Event> classroomEvents = this.eventRepository.findByClassroomId(classroom.getId());

        return new ClassroomWithEventsDTO(
            classroom.getId(),
            classroom.getDescription(),
            classroom.getBlock(),
            classroom.getFloor(),
            classroom.getCapacity(),
            classroom.getObservation(),
            classroom.getCreatedAt(),
            classroom.getUpdatedAt(),
            classroomEvents
        );
    }

    public List<Classroom> getClassroomsFromFloor(String floor) {
        if (floor.isBlank() || floor == null) throw new AppError("Floor value must be provided.", HttpStatus.BAD_REQUEST);
        return this.classroomRepository.findAllByFloor(floor);
    }

    public List<Course> getAllCoursesList() {
        return this.courseRepository.findAll();
    }

    public SearchQueryResponseDTO searchProfessorsAndClassrooms(String query) {
        if (query == null || query.isBlank()) throw new AppError("Query term must be provided.", HttpStatus.BAD_REQUEST);
        return new SearchQueryResponseDTO(
            this.professorRepository.searchProfessors(query),
            this.classroomRepository.searchClassrooms(query)
        );
    }

}
