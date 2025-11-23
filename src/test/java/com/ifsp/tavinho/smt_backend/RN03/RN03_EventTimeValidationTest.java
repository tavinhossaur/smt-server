package com.ifsp.tavinho.smt_backend.RN03;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.ifsp.tavinho.smt_backend.application.services.admin.EventService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

/**
 * Testes para a regra de negócio RN03:
 * A data de início do evento deve ser menor que a data de fim.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RN03 - Validação de Horários de Início e Fim do Evento")
class RN03_EventTimeValidationTest {

    @Mock
    private EventRepository eventRepository;
    
    @Mock
    private ClassroomRepository classroomRepository;
    
    @Mock
    private ProfessorRepository professorRepository;
    
    @Mock
    private CourseRepository courseRepository;
    
    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private CreateEventUseCase createEventUseCase;

    @InjectMocks
    private EventService eventService;

    private Professor professor;
    private Classroom classroom;
    private Course course;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        professor = new Professor("Eveline Baker", "eveline.baker@mold.com");
        professor.setId("prof111");

        classroom = new Classroom("T403", "T", "4", 30, "Sala teste");
        classroom.setId("class111");

        course = new Course("Biologia", "BIO");
        course.setId("course111");

        discipline = new Discipline("Microbiologia", "MBL", "course111");
        discipline.setId("disc111");

        // Mock padrão para os repositórios
        // Retorna um 'Optional' só para garantir a execução e evitar uma exception presente no CreateEventUseCase
        // que verifica se o ID da entidade relacionada ao evento já existe no banco.
        when(classroomRepository.findById(anyString())).thenReturn(Optional.of(classroom));
        when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(anyString())).thenReturn(Optional.of(discipline));
    }

    @Test
    @DisplayName("Deve permitir criar evento com horário de início menor que horário de fim")
    void shouldAllowEventCreationWithValidTimeRange() {

        // Arrange
        EventDTO eventDTO = new EventDTO(
            "Introdução ao reino fungi",
            Weekday.FRIDAY,
            "10:00",
            "12:00",
            "class111",
            "prof111",
            "disc111",
            "course111"
        );

        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );

        // Quando ele chamar o método para registrar no banco, ele retorna a entidade criada acima
        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.getStartTime().isBefore(result.getEndTime()));
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));  // Verifica se chamou o método execute uma única vez
    }

    @Test
    @DisplayName("Deve lançar exceção quando horário de início é igual ao horário de fim")
    void shouldThrowExceptionWhenStartTimeEqualsEndTime() {

        // Arrange
        EventDTO eventDTO = new EventDTO(
            "Evento Inválido",
            Weekday.FRIDAY,
            "10:00",
            "10:00", // Mesmo horário
            "class111",
            "prof111",
            "disc111",
            "course111"
        );

        // Act & Assert
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().equals("The start time can't be the same as the end time."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class)); // Verifica se nunca chamou o método execute do createEventUseCase
    }

    @Test
    @DisplayName("Deve lançar exceção quando horário de início é maior que horário de fim")
    void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {

        // Arrange
        EventDTO eventDTO = new EventDTO(
            "Evento Inválido",
            Weekday.FRIDAY,
            "14:00",
            "12:00", // Fim antes do início
            "class111",
            "prof111",
            "disc111",
            "course111"
        );

        // Act & Assert
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().equals("The start time and end time of the event are retroactive to each other."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento com diferença mínima de tempo (1 minuto)")
    void shouldAllowEventCreationWithMinimalTimeDifference() {

        // Arrange
        EventDTO eventDTO = new EventDTO(
            "Evento Curto",
            Weekday.FRIDAY,
            "10:00",
            "10:01", // Apenas 1 minuto de diferença
            "class111",
            "prof111",
            "disc111",
            "course111"
        );

        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );

        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.getStartTime().isBefore(result.getEndTime()));
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

}