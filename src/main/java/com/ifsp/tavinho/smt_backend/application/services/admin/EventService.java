package com.ifsp.tavinho.smt_backend.application.services.admin;

import java.util.List;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.UpdateEventUseCase;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.DeleteEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.FindEventUseCase;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.ListEventsUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ClassroomRepository classroomRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final DisciplineRepository disciplineRepository;

    private final CreateEventUseCase createEvent;
    private final UpdateEventUseCase updateEvent;
    private final DeleteEventUseCase deleteEvent;
    private final FindEventUseCase findEvent;
    private final ListEventsUseCase listEvents;

    public Event create(EventDTO input) {
        this.classroomRepository.findById(input.classroomId()).orElseThrow(() -> new EntityNotFoundException("Classroom not found with id: " + input.classroomId()));
        this.professorRepository.findById(input.professorId()).orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + input.professorId()));
        this.courseRepository.findById(input.courseId()).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + input.courseId()));
        this.disciplineRepository.findById(input.disciplineId()).orElseThrow(() -> new EntityNotFoundException("Discipline not found with id: " + input.disciplineId()));

        this.checkProfessorAvailability(input, input.professorId());
        this.checkClassroomAvailability(input, input.classroomId());

        this.checkInvalidTime(LocalTime.parse(input.startTime()), LocalTime.parse(input.endTime()));

        return this.createEvent.execute(input);
    }

    public Event update(EventDTO input, String id) {
        Event event = this.findEvent.execute(id);
        return this.updateEvent.execute(input, event);
    }

    public Boolean delete(String id) {
        Event event = this.findEvent.execute(id);
        return this.deleteEvent.execute(event);
    }

    public Event find(String id) {
        return this.findEvent.execute(id);
    }

    public List<Event> list() {
        return this.listEvents.execute(null);
    }

    private Boolean checkClassroomAvailability(EventDTO newEvent, String classroomId) {
        List<Event> events = this.eventRepository.findByClassroomId(classroomId);

        for (Event existingEvent : events) {
            if (!this.isAvailable(existingEvent, newEvent))
                throw new AppError("This classroom is already attached to an event at the desired day and time.", HttpStatus.CONFLICT);
        }

        return true;
    }

    private Boolean checkProfessorAvailability(EventDTO newEvent, String professorId) {
        List<Event> events = this.eventRepository.findByProfessorId(professorId);

        for (Event existingEvent : events) {
            if (!this.isAvailable(existingEvent, newEvent))
                throw new AppError("This professor is already attached to an event at the desired day and time.", HttpStatus.CONFLICT);
        }

        return true;
    }

    private Boolean isAvailable(Event existingEvent, EventDTO newEvent) {
        if (existingEvent.getWeekday() != newEvent.weekday()) return true;

        LocalTime newStart = LocalTime.parse(newEvent.startTime());
        LocalTime newEnd = LocalTime.parse(newEvent.endTime());

        LocalTime existingStart = existingEvent.getStartTime();
        LocalTime existingEnd = existingEvent.getEndTime();

        if (newStart.equals(existingEnd) || newEnd.equals(existingStart)) return true;

        boolean overlap = newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);

        return !overlap;
    }

    private Boolean checkInvalidTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new AppError(
                "The start time and end time of the event are retroactive to each other.",
                HttpStatus.BAD_REQUEST
            );
        }

        if (startTime.equals(endTime)) {
            throw new AppError(
                "The start time can't be the same as the end time.",
                HttpStatus.BAD_REQUEST
            );
        }

        return true;
    }
    
}
