package com.ifsp.tavinho.smt_backend.domain.usecases.event;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckAvailabilityUseCase implements UseCase<EventDTO, Boolean> {

    private final EventRepository eventRepository;

    public Boolean checkClassroomAvailability(EventDTO newEvent, String classroomId) {
        List<Event> events = this.eventRepository.findByClassroomId(classroomId);

        for (Event existingEvent : events) {
            if (!this.isAvailable(existingEvent, newEvent)) return false;
        }

        return true;
    }

    public Boolean checkProfessorAvailability(EventDTO newEvent, String professorId) {
        List<Event> events = this.eventRepository.findByProfessorId(professorId);

        for (Event existingEvent : events) {
            if (!this.isAvailable(existingEvent, newEvent)) return false;
        }

        return true;
    }

    private Boolean isAvailable(Event existingEvent, EventDTO newEvent) {
        if (existingEvent.getWeekday() == newEvent.weekday()) {
            if (
                LocalTime.parse(newEvent.startTime()).isBefore(existingEvent.getEndTime()) 
                && 
                LocalTime.parse(newEvent.endTime()).isAfter(existingEvent.getStartTime())
            ) return false;
        }

        return true;
    }

    @Override
    public Boolean execute(EventDTO input) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
