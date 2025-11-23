package com.ifsp.tavinho.smt_backend.RN02;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
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
 * Testes para a regra de negócio RN02:
 * Salas só podem estar alocados a um único evento em um mesmo dia da semana/horário.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RN02 - Validação de Conflito de Horário de Salas")
class RN02_ClassroomConflictTest {

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

    private EventDTO eventDTO;
    private Professor professor;
    private Classroom classroom;
    private Course course;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        professor = new Professor("Ada Wong", "ada.wong@umbrella.com");
        professor.setId("prof456");

        classroom = new Classroom("T402", "T", "4", 40, "Sala teste");
        classroom.setId("class789");

        course = new Course("T-Virus 101", "V101");
        course.setId("course456");

        discipline = new Discipline("Arte da Mentira", "ATM", "course456");
        discipline.setId("disc789");

        eventDTO = new EventDTO(
            "Introdução a Lábia",
            Weekday.WEDNESDAY,
            "14:00",
            "16:00",
            "class789",
            "prof456",
            "disc789",
            "course456"
        );

        // Mock padrão para os repositórios
        // Retorna um 'Optional' só para garantir a execução e evitar uma exception presente no CreateEventUseCase
        // que verifica se o ID da entidade relacionada ao evento já existe no banco.
        when(classroomRepository.findById(anyString())).thenReturn(Optional.of(classroom));
        when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(anyString())).thenReturn(Optional.of(discipline));
    }

    @Test
    @DisplayName("Deve permitir criar evento quando sala não tem conflito de horário")
    void shouldAllowEventCreationWhenNoClassroomConflict() {

        // Arrange
        // Retorna sempre um array vazio de eventos relacionados aquela sala, mesmo que a sala
        // tivesse de fato, eventos cadastrados e relacionados a ela.
        when(eventRepository.findByClassroomId(anyString())).thenReturn(Arrays.asList());

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
        assertEquals(eventDTO.classroomId(), result.getClassroomId());
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class)); // Verifica se chamou o método execute uma única vez
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito de horário no mesmo dia")
    void shouldThrowExceptionWhenClassroomHasExactConflict() {

        // Arrange
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));

        // Act & Assert
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().equals("This classroom is already attached to an event at the desired day and time."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class)); // Verifica se nunca chamou o método execute do createEventUseCase
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito com sobreposição parcial no horário de início")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtStart() {
        // Arrange - evento existente de 13:00 às 15:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("13:00"),
            LocalTime.parse("15:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 14:00 às 16:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(createEventUseCase, never()).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito com sobreposição parcial no horário de fim")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtEnd() {
        // Arrange - evento existente de 15:00 às 17:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("15:00"),
            LocalTime.parse("17:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 14:00 às 16:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(createEventUseCase, never()).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento com a sala em dia diferente mesmo com horário igual")
    void shouldAllowEventCreationOnDifferentDaySameTime() {

        // Arrange - evento existente na terça-feira
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.TUESDAY, // Dia diferente
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
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
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente após sem sobreposição")
    void shouldAllowEventCreationImmediatelyAfter() {
        // Arrange - evento existente de 12:00 às 14:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("12:00"),
            LocalTime.parse("14:00"), // Termina quando o novo (eventDTO) começa
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
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
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

        @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente antes sem sobreposição")
    void shouldAllowEventCreationImmediatelyBefore() {
        // Arrange - evento existente de 12:00 às 14:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("16:00"), // Inícia quando o novo (eventDTO) termina
            LocalTime.parse("18:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
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
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

}