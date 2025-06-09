package com.ifsp.tavinho.smt_backend.domain.dtos.output;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;

public record ProfessorWithEventsDTO(Professor professor, List<Event> events) { }
