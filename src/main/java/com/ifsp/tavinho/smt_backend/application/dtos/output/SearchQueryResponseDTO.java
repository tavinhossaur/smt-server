package com.ifsp.tavinho.smt_backend.application.dtos.output;

import java.util.List;

import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;

public record SearchQueryResponseDTO(List<Professor> professors, List<Classroom> classrooms) { }
